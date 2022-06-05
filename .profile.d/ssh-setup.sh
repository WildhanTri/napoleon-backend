# file: .profile.d/ssh-setup.sh

#!/bin/bash
echo $0: creating public and private key files

# Create the .ssh directory
mkdir -p ${HOME}/.ssh
chmod 700 ${HOME}/.ssh

# Create the public and private key files from the environment variables.
echo "${HEROKU_PUBLIC_KEY}" > ${HOME}/.ssh/id_rsa.pub
chmod 644 ${HOME}/.ssh/id_rsa.pub

# Note use of double quotes, required to preserve newlines
echo "${HEROKU_PRIVATE_KEY}" > ${HOME}/.ssh/id_rsa
chmod 600 ${HOME}/.ssh/id_rsa

# Preload the known_hosts file  (see "version 2" below)
echo '|1|luBqutGcP9OlzNqReApvZugJj+g=|AZvrLmQp6EOM6mQyK1WKhrHUAtw= ecdsa-sha2-nistp256 AAAAE2VjZHNhLXNoYTItbmlzdHAyNTYAAAAIbmlzdHAyNTYAAABBBPqjsu9kvld5vNqBlpbD0mOf7KTEFAPtbJ1gFVMVHUCEZXihg2x5EVBBuilyLW7N1D6wkj2d8eUjfQVptrmBNZg=' > ${HOME}/.ssh/known_hosts

# Start the SSH tunnel if not already running
SSH_CMD="ssh -v -f -i ${HOME}/.ssh/id_rsa -N -L 3307:${REMOTE_MYSQL_HOST}:3306 ${REMOTE_USER}@${REMOTE_SITE}"
echo SSH_CMD

PID=`pgrep -f "${SSH_CMD}"`
if [ $PID ] ; then
    echo $0: tunnel already running on ${PID}
else
    echo $0 launching tunnel
    $SSH_CMD
fi