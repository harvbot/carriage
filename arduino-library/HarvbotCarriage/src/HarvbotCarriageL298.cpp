#include <Arduino.h>
#include <HarvbotMotorBase.h>
#include <HarvbotMotorL289.h>

#include "HarvbotCarriageL298.h"

HarvbotCarriageL298::HarvbotCarriageL298(HarvbotMotorL289Pins leftMotorPins, HarvbotMotorL289Pins rightMotorPins)
{
	this->m_leftMotor = new HarvbotMotorL289(leftMotorPins);
	this->m_rightMotor = new HarvbotMotorL289(rightMotorPins);
}

HarvbotCarriageL298::~HarvbotCarriageL298() 
{
}