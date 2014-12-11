To Do
=====

- check for a black line on the floor (using light sensor)
	- while the sensor detects black
		set blackDetected true
		if sensor doesn't detect black
			set blackDetected false
	- while the sensor doesn't detect black
		
		

- check for obstacles in front of the robot (using ultrasonic sensor)
	-while the obstacle is detected
		set obstacleDetected true
		- if !obstacleDetected
			set obstacleDetected false

- while obstacleDetected
		turn left

- while blackDetected
		move forwards
		turn left
			scan



- default: move forwards

- when sensor detects black, turn left, scan - repeat until black detected
	
