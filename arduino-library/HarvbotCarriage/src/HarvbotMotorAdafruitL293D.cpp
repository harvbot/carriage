#include <Arduino.h>
#include <AFMotor.h>
#include "HarvbotMotorAdafruitL293D.h"

HarvbotMotorAdafruitL293D::HarvbotMotorAdafruitL293D(int8_t motorNumber, uint8_t freq = DC_MOTOR_PWM_RATE) 
{
	this->internalMotor = new AF_DCMotor(motorNumber, freq);
}

HarvbotMotorAdafruitL293D::~HarvbotMotorAdafruitL293D()
{
    if(this->internalMotor != NULL)
    {
        delete this->internalMotor;
    }
}

void HarvbotMotorAdafruitL293D::stop() 
{
	this->internalMotor->run(RELEASE);
}

void HarvbotMotorAdafruitL293D::setSpeed(int speed) 
{
	if(speed < 0)
	{
		speed = 0;
	}
	else if (speed > 255)
	{
		speed = 255;
	}
	
	this->internalMotor->setSpeed(speed);

	HarvbotMotorBase::setSpeed(speed);
}

void HarvbotMotorAdafruitL293D::setDirection(int direction) 
{
	if(direction == CARRIAGE_DIRECTION_FORWARD)
	{
        this->internalMotor->run(FORWARD);
	}
	else 
	{
		this->internalMotor->run(BACKWARD);
	}
	
	HarvbotMotorBase::setDirection(direction);
}