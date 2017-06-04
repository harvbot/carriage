#ifndef HarvbotMotorAdafruitL293D_H_
#define HarvbotMotorAdafruitL293D_H_

#include <Arduino.h>
#include <HarvbotMotorBase.h>
#include <AFMotor.h>

class HarvbotMotorAdafruitL293D : public HarvbotMotorBase {
	private:
	
		// Stores internal motor instance.
		AF_DCMotor* internalMotor;
		
	public:
	
		/**
		 * Initializes new instance of the HarvbotMotorAdafruitL293D class.
		 */
		HarvbotMotorAdafruitL293D(int8_t motorNumber, uint8_t freq = DC_MOTOR_PWM_RATE);
		
		/**
		 * Destructor.
		 */
		~HarvbotMotorAdafruitL293D();
		
		/**
		 * Stops motor.
		 */
		void stop();
		
		/**
		 * Sets current motor speed.
		 */
		void setSpeed(int speed);

		/**
		 * Sets current motor direction.
		 */
		void setDirection(int direction);
};

#endif /* HarvbotMotorAdafruitL293D_H_ */