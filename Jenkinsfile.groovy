node {
   properties([parameters([string(defaultValue: '127.0.0.1', description: 'Please give an IP to host website', name: 'DEVIP', trim: true)])])


 stage("Pull git") {
    git "git@github.com:akadyrov86/website.git"
 }

 statge("Install Apache"){
  sh "ssh ec2-iser@${DEVIP}   sudo yum install httpd -y"
 }

 stage("Start Apache"){
   sh "ssh ec2-user@${DEVIP}  sudo systemctl start httpd"
 }

 stage("Copy files"){
   sh "rsync -aP --delete index.html  ec2-user@${DEVIP}:/tmp"
 }

 stage("Move files"){
   sh "ssh ec2-user@${DEVIP}   sudo cp -f /tmp/index.html  /var/www/html/index.html"   
 }

}