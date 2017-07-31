#All curl commands assume you are in the box/conf/ directory.

#!/bin/sh

USER=rest-admin
PASS=cisco123
DB=outdoor-explorer

#Create a REST API instance: 
#curl -v -X POST  --anyauth --user $USER:$PASS \
#	 -H "Content-Type:application/json" -d @./create_restapi.json  \
#	 http://localhost:8002/v1/rest-apis

#Enable collection lexicon:
#curl -v -X PUT --anyauth -u $USER:$PASS  \
#	 -H "Content-Type:application/json" -d @./collection-lexicion.json  \
#	 http://localhost:8002/manage/v2/databases/$DB/properties

#Add range path indexes:
curl -v -X PUT --anyauth --user $USER:$PASS  -i \
	 -H "Content-type: application/json" -d @./explorer_indexes.json \
	 http://localhost:8002/manage/LATEST/databases/$DB/properties
