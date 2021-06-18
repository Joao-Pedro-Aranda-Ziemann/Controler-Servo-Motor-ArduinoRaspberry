#include <Servo.h>

int posicao = 0;
int posicaoAnterior = 0;

Servo servo;

void setup() {

	Serial.begin(9600);
	servo.attach(7);

	servo.write(0);

}

void loop() {

	posicao = Serial.parseInt();

	if (posicao > 0 && posicao <= 180 && posicao != posicaoAnterior && posicao != 0) {

		servo.write(posicao);
		posicaoAnterior = posicao;
		delay(500);
	}
}
