#include "HarvbotMotorBase.h"

HarvbotMotorBase::HarvbotMotorBase() {
}

HarvbotMotorBase::~HarvbotMotorBase() {
}

void HarvbotMotorBase::stop()
{
}

int HarvbotMotorBase::getSpeed()
{
	return m_Speed;
}

void HarvbotMotorBase::setSpeed(int speed)
{
	m_Speed = speed;
}

int HarvbotMotorBase::getDirection()
{
	return m_Direction;
}

void HarvbotMotorBase::setDirection(int direction)
{
	m_Direction = direction;
}

void HarvbotMotorBase::forward()
{
	setDirection(CARRIAGE_DIRECTION_FORWARD);
}

void HarvbotMotorBase::backward()
{
	setDirection(CARRIAGE_DIRECTION_BACKWARD);
}

