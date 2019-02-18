# ContactForm
### The submit script for "Contact Me" form of "Read Only" template from html5up.net

To add the ability of sending data with animated responce to "Read Only" template:

- Put the content of contactform.zip into directory which contains index.html of template.
- Add ```<script type="text/javascript" language="javascript" src="contactform/contactform.nocache.js"></script> ```
  inside ```<head>``` in index.html
- Edit submit.php. Put your e-mail address at 2nd line inside ''.

If you want change messages and intervals add this inside ```<head>```:
```
		<script>
			contactform_ok = "Thank you! The message has been sent.";
			contactform_error = "Ooops! Something has gone wrong. Try again later.";
			contactform_message_appearance_interval = "1000";
			contactform_message_wait_interval = "1000";
			contactform_message_disappearance_interval = "1000";
		</script>
```
and edit values.

How it works: https://youtu.be/mChY9IOfK-g

Live example: http://www.e-graphite.com/pipedblocks

