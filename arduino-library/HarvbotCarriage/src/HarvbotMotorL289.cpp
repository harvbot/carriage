#include <Arduino.h>
#include "HarvbotMotorL289.h"

HarvbotMotorL289::HarvbotMotorL289(int pinEnable, int pinIn1, int pinIn2) 
{
	init(pinEnable, pinIn1, pinIn2);
}

HarvbotMotorL289::HarvbotMotorL289(HarvbotMotorL289Pins pins)
{
	init(pins.PinEnable, pins.PinIN1, pins.PinIN2);
}

HarvbotMotorL289::~HarvbotMotorL289()
{
}

void HarvbotMotorL289::init(int pinEnable, int pinIn1, int pinIn2)
{
	m_pinEnable = pinEnable;
	m_pinIn1 = pinIn1;
	m_pinIn2 = pinIn2;
	
	pinMode(pinEnable, OUTPUT);
	pinMode(pinIn1, OUTPUT);
	pinMode(pinIn2, OUTPUT);
}

void HarvbotMotorL289::stop() 
{
	digitalWrite(m_pinIn1, LOW);
	digitalWrite(m_pinIn2, LOW);
}

void HarvbotMotorL289::setSpeed(int speed) 
{
	if(speed < 0)
	{
		speed = 0;
	}
	else if (speed > 255)
	{
		speed = 255;
	}
	
	analogWrite(m_pinEnable, speed);
	HarvbotMotorBase::setSpeed(speed);
}

void HarvbotMotorL289::setDirection(int direction) 
{
	if(direction == CARRIAGE_DIRECTION_FORWARD)
	{
		digitalWrite(m_pinIn1, LOW);
		digitalWrite(m_pinIn2, HIGH);
	}
	else 
	{
		digitalWrite(m_pinIn1, HIGH);
		digitalWrite(m_pinIn2, LOW);
	}
	
	HarvbotMotorBase::setDirection(direction);
}