#!/bin/sh

GREEN='\033[0;32m'
RED='\e[0;31m'
PURPLE='\033[0;35m'
CYAN='\033[0;36m'
YELLOW='\033[0;33m'

# Get the name of the Release file to execute
release_file=$1

# Get the list of MySQL files to execute from the text file
mysql_files=$(cat "$release_file")

#echo "$mysql_files"

# Check if any MySQL files were specified
if [ -z "$mysql_files" ]; then
  echo "${RED}Error: No MySQL files specified in $release_file."
  exit 1
fi

# Execute each MySQL file
while read -r mysql_file || [ -n "$mysql_file" ]; do

  # shellcheck disable=SC2039
  if [[ "$mysql_file" == --* || "$mysql_file" == "" ]]; then
    continue
  fi

  if ! [[ "$mysql_file" == *.sql ]]; then
    echo "${YELLOW}Skipping: file ${CYAN}$mysql_file"
    continue
  fi

  # Check if the MySQL file exists
  if ! [ -f "$mysql_file" ]; then
    echo "${RED}Error: MySQL file '$mysql_file' does not exist, Deployment Completed Partially for: $release_file !!!"
    exit 1
  fi

  # Execute the MySQL file
  echo "${PURPLE}Executing: MySQL file ${CYAN}$mysql_file"
  mysql < "$mysql_file"

  # Check if the MySQL file executed successfully
  if [ $? -ne 0 ]; then
    echo "${RED}Error: MySQL file $mysql_file failed to execute."
    exit 1
  fi
done < "$release_file"

echo "${GREEN}\nDeployment Completed Successfully for: $release_file"

# Exit the script successfully
exit 0