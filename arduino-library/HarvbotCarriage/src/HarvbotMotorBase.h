#ifndef HarvbotMotorBase_H_
#define HarvbotMotorBase_H_

#include <Arduino.h>

#define CARRIAGE_DIRECTION_FORWARD 1

#define CARRIAGE_DIRECTION_BACKWARD -1

class HarvbotMotorBase {
	private:
		// Stores motor current speed value.
		int m_Speed;
		
		// Stores motor current direction.
		int m_Direction;
	public:
	
		/**
		 * Initializes new instance of the HarvbotMotorBase class.
		 */
		HarvbotMotorBase();
		
		/**
		 * Destructor.
		 */
		virtual ~HarvbotMotorBase();
		
		/**
		 * Stops motor.
		 */
		virtual void stop();
		
		/**
		 * Gets current motor speed.
		 */
		virtual int getSpeed();
		
		/**
		 * Sets current motor speed.
		 */
		virtual void setSpeed(int speed);
		
		/**
		 * Gets current motor direction.
		 */
		virtual int getDirection();
		
		/**
		 * Sets current motor direction.
		 */
		virtual void setDirection(int direction);
		
		/**
		 * Moves motor forward.
		 */
		void forward();
		
		/**
		 * Moves motor backward.
		 */
		void backward();
};

#endif /* HarvbotMotorBase_H_ */