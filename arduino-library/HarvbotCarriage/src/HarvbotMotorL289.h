#ifndef HarvbotMotorL289_H_
#define HarvbotMotorL289_H_

#include <Arduino.h>
#include <HarvbotMotorBase.h>

struct HarvbotMotorL289Pins
{
	int PinEnable;
	
	int PinIN1;
	
	int PinIN2;
};

class HarvbotMotorL289 : public HarvbotMotorBase {
	private:
	
		// Stores enable(speed) pin number.
		int m_pinEnable;
		
		// Stores IN1 pin number.
		int m_pinIn1;
		
		// Stores IN2 pin number.
		int m_pinIn2;
		
		/**
		 * Initializes class fields.
		 */
		void init(int pinEnable, int pinIn1, int pinIn2);
	public:
	
		/**
		 * Initializes new instance of the HarvbotMotorL289 class.
		 */
		HarvbotMotorL289(int pinEnable, int pinIn1, int pinIn2);
		
		/**
		 * Initializes new instance of the HarvbotMotorL289 class.
		 */
		HarvbotMotorL289(HarvbotMotorL289Pins pins);
		
		/**
		 * Destructor.
		 */
		~HarvbotMotorL289();
		
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

#endif /* HarvbotMotorL289_H_ */