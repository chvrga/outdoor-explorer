
(: forest :)
xquery version "1.0-ml";
import module namespace admin = "http://marklogic.com/xdmp/admin" at "/MarkLogic/admin.xqy";
let $config := admin:get-configuration()
let $config := admin:forest-create(
  $config, 
  "outdoor-explorer-01",
  xdmp:host(), 
  ())
return admin:save-configuration($config);

(: database :)
xquery version "1.0-ml";
import module namespace admin = "http://marklogic.com/xdmp/admin" at "/MarkLogic/admin.xqy";
let $config := admin:get-configuration()
let $config := admin:database-create(
  $config,
  "outdoor-explorer",
  xdmp:database("Security"),
  xdmp:database("Schemas"))
return admin:save-configuration($config);

(: attach forest to database :)
xquery version "1.0-ml";
import module namespace admin = "http://marklogic.com/xdmp/admin" at "/MarkLogic/admin.xqy";
let $config := admin:get-configuration()
let $config := admin:database-attach-forest(
  $config,
  xdmp:database("outdoor-explorer"), 
  xdmp:forest("outdoor-explorer-01"))
return admin:save-configuration($config);

(: application server :)
xquery version "1.0-ml";
import module namespace admin = "http://marklogic.com/xdmp/admin" at "/MarkLogic/admin.xqy";
let $config := admin:get-configuration()
let $groupid := admin:group-get-id($config, "Default")
let $server := admin:http-server-create(
  $config, 
  $groupid,
  "8050-outdoor-explorer", 
  "C:\Users\ivana.frankic\workspace\projects\outdoor-explorer",
  8050,
  0,
  admin:database-get-id($config, "outdoor-explorer"))
return admin:save-configuration($server);

(: Create XDBC application server for loading data with mlcp :)
xquery version "1.0-ml";
import module namespace admin = "http://marklogic.com/xdmp/admin" at "/MarkLogic/admin.xqy";
let $config := admin:get-configuration()
let $groupid := admin:group-get-id($config, "Default")
let $server := admin:xdbc-server-create(
    $config, 
    $groupid,
    "outdoor-explorer-XDBC", 
    "/",
    8056,
    0,
    admin:database-get-id($config, "outdoor-explorer"))
return admin:save-configuration($server);
