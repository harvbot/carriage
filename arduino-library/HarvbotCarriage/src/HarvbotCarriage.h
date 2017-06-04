#include <Arduino.h>
#include <HarvbotMotorBase.h>

#ifndef HarvbotCarriage_H_
#define HarvbotCarriage_H_

class HarvbotCarriage {
	protected:
		// Stores the left motor.
		HarvbotMotorBase* m_leftMotor;
		
		// Stores the right motor.
		HarvbotMotorBase* m_rightMotor;
		
	public:

		/**
		 * Destructor.
		 */
		~HarvbotCarriage();
		
		/**
		 * Stops carriage.
		 */
		void stop();
		
		/**
		 * Moves carriage forward.
		 */
		void forward();
		
		/**
		 * Moves carriage backward.
		 */
		void backward();
		
		/**
		 * Turns carriage left.
		 */
		void turnLeft();
		
		/**
		 * Turns carriage rigth.
		 */
		void turnRight();
		
		/**
		 * Gets carriage speed.
		 */
		int getSpeed();
		
		/**
		 * Sets carriage speed.
		 */
		void setSpeed(int speed);
};

#endif /* HarvbotCarriage_H_ */
