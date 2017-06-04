#include <Arduino.h>
#include <HarvbotMotorBase.h>
#include <HarvbotCarriage.h>

#ifndef HarvbotCarriageL298_H_
#define HarvbotCarriageL298_H_

class HarvbotCarriageL298 : public HarvbotCarriage {
	public:
	
		/**
		 * Initializes new instance of the HarvbotCarriage class.
		 */
		HarvbotCarriageL298(HarvbotMotorL289Pins leftMotor, HarvbotMotorL289Pins rightMotor);
	
		/**
		 * Destructor.
		 */
		~HarvbotCarriageL298();
};

#endif /* HarvbotCarriageL298_H_ */
