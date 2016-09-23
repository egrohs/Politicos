#!/usr/bin/env python
# Original: https://gist.github.com/manchicken/6308596
# Adapted by lucasa@gmail.com

import sys, getopt, csv, pprint
from pymongo import MongoClient

global_mongo = None
global_db = None
global_coll = None

def usage(msg):
  if msg != None:
    print '\nError: {msg}'.format(msg=msg)
  
  print '\nUsage: {arg0} [options]\n'.format(arg0=sys.argv[0])
  print '  Options:\n'
  print '      -h ......................... Display this help message\n'
  print '      -f|--file=CSVFILE .......... The CSV file to load\n'
  print '      -m|--mongo=MONGOINSTANCE.COLLECTION ... The mongo instance to load the file into\n'
  sys.exit(0)

def add_record_to_mongo(mongo, record):
  global global_mongo
  global global_db
  global global_coll
  
  mongo_bits = mongo.split('.')
  mongo_db = mongo_bits[0]
  mongo_coll = mongo_bits[1]
  print 'Load a record!\n'
  
  if global_mongo == None: global_mongo = MongoClient()
  if global_db == None: global_db = global_mongo[mongo_db]
  if global_coll == None: global_coll = global_db[mongo_coll]
  
  mixed = {}
  urls = []
  positions = []
  for k in record:
      if 'url' in k:
          if len(record[k]) > 0:
              urls.append(record[k])
      elif 'position' in k:	
          if len(record[k]) > 0:
	      positions.append(record[k])
      else:
          if len(k) > 0:
              mixed[k] = record[k]

  mixed['urls'] = ''
  if len(urls) > 1:
      mixed['urls'] = ','.join(urls)
  elif len(urls) == 1:
      mixed['urls'] = urls[0]
  mixed['urls'] = mixed['urls'].replace("\n", ",")

  mixed['positions'] = ''
  if len(positions) > 1:
      mixed['positions'] = ','.join(positions)
  elif len(positions) == 1:
      mixed['positions'] = positions[0]
  mixed['positions'] = mixed['positions'].replace("\n", ",")

  pprint.pprint(mixed)

  # Now let's insert
  print 'DB: {db} and COLL: {coll}'.format(db=global_db,coll=global_coll)
  global_coll.insert(mixed)

def run_csv_file(csvfile, mongo):
  print 'Gonna load the CSV file "{csvfile}" into mongodb "{mongo}"\n'.format(csvfile=csvfile, mongo=mongo)
  with open(csvfile,'rb') as incsv:
    parsed = csv.DictReader(incsv, delimiter=',', quotechar='"')
    for record in parsed:
      add_record_to_mongo(mongo, record)

def main(argv):
  csvfile=None
  mongo=None
  
  try:
    opts, args = getopt.getopt(argv, 'hf:m:',['file=','mongo='])
  except getopt.GetoptError:
    usage(None)
  
  for one, arg in opts:
    if one == '-h':
      usage(None)
    elif one in ('-f','--file'):
      csvfile = arg
    elif one in ('-m','--mongo'):
      mongo = arg
  
  if csvfile == None: usage('Missing CSV file')
  if mongo == None: usage('Missing mongo')
  
  run_csv_file(csvfile, mongo)

if __name__ == '__main__':
  main(sys.argv[1:])
