#!/bin/sh

source ~/.profile

echo "imprting lists.."
mlcp import -options_file import_conn.conf -input_file_path export_xml/list -output_collections list


echo "importing users.."
mlcp import -options_file import_conn.conf -input_file_path export_xml/user -output_collections user

echo "importing destinations.."
mlcp import -options_file import_conn.conf -input_file_path export_xml/explore -output_collections explore

echo "ALL DONE"

