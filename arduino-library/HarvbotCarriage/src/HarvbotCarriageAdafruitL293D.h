#include <Arduino.h>
#include <HarvbotMotorBase.h>
#include <HarvbotCarriage.h>
#include <AFMotor.h>

#ifndef HarvbotCarriageAdafruitL293D_H_
#define HarvbotCarriageAdafruitL293D_H_

class HarvbotCarriageAdafruitL293D : public HarvbotCarriage {
	public:
	
		/**
		 * Initializes new instance of the HarvbotCarriage class.
		 */
		HarvbotCarriageAdafruitL293D();
	
		/**
		 * Destructor.
		 */
		~HarvbotCarriageAdafruitL293D();
};

#endif /* HarvbotCarriageAdafruitL293D_H_ */
