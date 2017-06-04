#include <Arduino.h>
#include <HarvbotMotorBase.h>
#include <HarvbotMotorAdafruitL293D.h>

#include "HarvbotCarriageAdafruitL293D.h"

HarvbotCarriageAdafruitL293D::HarvbotCarriageAdafruitL293D()
{
	this->m_leftMotor = new HarvbotMotorAdafruitL293D(1, MOTOR12_64KHZ);
	this->m_rightMotor = new HarvbotMotorAdafruitL293D(2, MOTOR12_64KHZ);
}

HarvbotCarriageAdafruitL293D::~HarvbotCarriageAdafruitL293D() 
{
}