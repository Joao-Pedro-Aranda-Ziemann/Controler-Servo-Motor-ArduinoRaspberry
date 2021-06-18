import serial
import time
import os
import sys


for i in range(2):
	comunicacaoSerial = serial.Serial('/dev/ttyACM0', 9600)
	comunicacaoSerial.write(str(sys.argv[1]))
	time.sleep(2)
print 'Sucesso'
