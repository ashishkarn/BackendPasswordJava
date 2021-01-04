import smtplib
import ssl
import sys


# User configuration
sender_email = sys.argv[1]
receiver_email = 'ashish.karan96@gmail.com'
password = '#######'
passwordTO="Your new password:{0}".format(sys.argv[2])



# Email text
email_body = "\r\n".join([
  "Your new Password:",
  "",
  passwordTO
  ])

# Creating a SMTP session | use 587 with TLS, 465 SSL and 25
server = smtplib.SMTP('smtp.gmail.com', 587)
# Encrypts the email
context = ssl.create_default_context()
server.starttls(context=context)
# We log in into our Google account
server.login(sender_email, password)
# Sending email from sender, to receiver with the email body
server.sendmail(sender_email, receiver_email, email_body)
print('Email sent!')

print('Closing the server...')
server.quit()
