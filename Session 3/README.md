Android - Session Three
=======================
Sensors in Action
-----------------

[Presentation](https://docs.google.com/presentation/d/1AMZ-GWRgYBjXBOjt6QS9qsLo3jCCxB18wVxlLiGXoIo/edit?usp=sharing)


[Demo App](https://www.dropbox.com/s/peebf44x7zhcayo/AppSocThree.apk)

Objectives
----------
- explore the range and types of sensors available on Android
- learn how to request for, receive and process sensor data
- observe how to aggregate data from multiple sensors
- see some sensors in action in live demos

Suggested Further Exercises
---------------------------
1. Create a sensor-toolkit app.
	- Suggestions:
		- Display all the available in a nice list, with additional metadata like vendor, type, resolution, etc.
		- Upon clicking on one of the sensors, register as the sensor's listener and report its values to the user!
		- Tailor fancier value displays for common sensors like ACCELEROMETER and LIGHT.
		- Offer specific tools like Compass, Thermometer, etc.
	- Hints:
		- Use [SensorManager#getSensorList()](http://developer.android.com/guide/topics/sensors/sensors_overview.html#sensors-identify) to retrieve all the sensors on a device.
		- Use a ListActivity for the list of sensors, and launch a new Activity when a sensor is selected to display its values.
		- Pass in the chosen sensor using [Intent extras](http://developer.android.com/guide/components/intents-filters.html#ExampleExplicit) to the data display Activity.
2. Make a shake-driven browser.
	- Suggestions:
		- Use the Android [WebView](http://developer.android.com/reference/android/webkit/WebView.html) as the central UI element.
		- Refresh pages by [detecting shakes](https://github.com/square/seismic).
		- Navigate Back and Forward by detecting device tilt.
	- Hints:
		- Check out the [CompassActivity](https://github.com/ICAppSoc/Android-Sessions/blob/master/Session%203/src/uk/ac/icappsoc/appsocthree/compass/CompassActivity.java) and [Gravity2dActivity](https://github.com/ICAppSoc/Android-Sessions/blob/master/Session%203/src/uk/ac/icappsoc/appsocthree/accel/Gravity2DActivity.java) demos.
		- Use LogCat or Toast messages for debugging the values you receive from your sensors.
3. Make a tilt-controlled ball-maze-like game.
	- Suggestions:
		- Keep it 2D, and simply grab the X and Y values returned by Sensor.TYPE_GRAVITY.
		- Move the ball to hit targets, enter goals.
		- Add multiple balls, try to navigate them all simultaneously.
	- Hints:
		- Check out the [Gravity2dActivity](https://github.com/ICAppSoc/Android-Sessions/blob/master/Session%203/src/uk/ac/icappsoc/appsocthree/accel/Gravity2DActivity.java) demo.
		- Use a custom View to do your drawing on a Canvas.
		- Manage an [ArrayList](http://www.tutorialspoint.com/java/java_arraylist_class.htm) of Ball objects, each with a position, radius, and color.
