#!\bin\sh

source ~\.profile

echo "IMPRTING LISTS.."
mlcp import -options_file import_conn.conf -input_file_path C:\\Users\\ivana.frankic\\workspace\\outdoor-explorer\\xqy\\box\\data\\list -output_collections list

echo "IMPORTING USERS.."
mlcp import -options_file import_conn.conf -input_file_path C:\\Users\\ivana.frankic\\workspace\\outdoor-explorer\\xqy\\box\\data\\user -output_collections user

echo "IMPORTING DESTINATIONS.."
mlcp import -options_file import_conn.conf -input_file_path C:\\Users\\ivana.frankic\\workspace\\outdoor-explorer\\xqy\\box\\data\\explore -output_collections explore

echo "IMPORTING AVATARS.."
mlcp import -options_file import_conn.conf -input_file_path C:\\Users\\ivana.frankic\\workspace\\outdoor-explorer\\xqy\\box\\data\\images\\avatars -output_collections avatars

echo "IMPORTING BACKGROUNDS.."
mlcp import -options_file import_conn.conf -input_file_path C:\\Users\\ivana.frankic\\workspace\\outdoor-explorer\\xqy\\box\\data\\images\\backgrounds -output_collections backgrounds

echo "IMPORTING LOGOS.."
mlcp import -options_file import_conn.conf -input_file_path C:\\Users\\ivana.frankic\\workspace\\outdoor-explorer\\xqy\\box\\data\\images\\logos -output_collections logos


echo "ALL DONE"

