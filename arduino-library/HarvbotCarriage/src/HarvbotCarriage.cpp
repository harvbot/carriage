#include <Arduino.h>
#include <HarvbotMotorBase.h>

#include "HarvbotCarriage.h"

HarvbotCarriage::~HarvbotCarriage() 
{
	delete this->m_leftMotor;
	delete this->m_rightMotor;
}

void HarvbotCarriage::stop() 
{
	this->m_leftMotor->stop();
	this->m_rightMotor->stop();
}

void HarvbotCarriage::forward() 
{
	this->m_leftMotor->forward();
	this->m_rightMotor->forward();
}

void HarvbotCarriage::backward() 
{
	this->m_leftMotor->backward();
	this->m_rightMotor->backward();
}

void HarvbotCarriage::turnLeft() 
{
	this->m_leftMotor->forward();
	this->m_rightMotor->stop();
}

void HarvbotCarriage::turnRight() 
{
	this->m_leftMotor->stop();
	this->m_rightMotor->forward();
}

int HarvbotCarriage::getSpeed() 
{
	return this->m_leftMotor->getSpeed();
}

void HarvbotCarriage::setSpeed(int speed) 
{
	this->m_leftMotor->setSpeed(speed);
	this->m_rightMotor->setSpeed(speed);
}


