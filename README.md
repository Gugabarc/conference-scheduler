# conference-scheduler

## Problem Statement - Conference Track Management

You are planning a big programming conference and have received many proposals which have passed the initial screen process but you're having trouble fitting them into the time constraints of the day -- there are so many possibilities! So you write a program to do it for you.

- Don't use plugins utilities for date calculate: Joda-Time
- Use input data: input.txt - you should use this file for input data on system
- The conference has multiple tracks each of which has a morning and afternoon session.
- Each session contains multiple talks.
- Morning sessions begin at 9am and must finish by 12 noon, for lunch.
- Afternoon sessions begin at 1pm and must finish in time for the networking event.
- The networking event can start no earlier than 4:00 and no later than 5:00.
- No talk title has numbers in it.
- All talk lengths are either in minutes (not hours) or lightning (5 minutes).
- Presenters will be very punctual; there needs to be no gap between sessions.

Note that depending on how you choose to complete this problem, your solution may give a different ordering or combination of talks into tracks. This is acceptable; you don’t need to exactly duplicate the sample output given here.

### Test input:

Writing Fast Tests Against Enterprise Rails 60min

Overdoing it in Python 45min

Lua for the Masses 30min

Ruby Errors from Mismatched Gem Versions 45min

Common Ruby Errors 45min

Rails for Python Developers lightning

Communicating Over Distance 60min

Accounting-Driven Development 45min

Woah 30min

Sit Down and Write 30min

Pair Programming vs Noise 45min

Rails Magic 60min

Ruby on Rails: Why We Should Move On 60min

Clojure Ate Scala (on my project) 45min

Programming in the Boondocks of Seattle 30min

Ruby vs. Clojure for Back-End Development 30min

Ruby on Rails Legacy App Maintenance 60min

A World Without HackerNews 30min

User Interface CSS in Rails Apps 30min



### Test output:

Track 1:

09:00AM Writing Fast Tests Against Enterprise Rails 60min

10:00AM Overdoing it in Python 45min

10:45AM Lua for the Masses 30min

11:15AM Ruby Errors from Mismatched Gem Versions 45min

12:00PM Lunch

01:00PM Ruby on Rails: Why We Should Move On 60min

02:00PM Common Ruby Errors 45min

02:45PM Pair Programming vs Noise 45min

03:30PM Programming in the Boondocks of Seattle 30min

04:00PM Ruby vs. Clojure for Back-End Development 30min

04:30PM User Interface CSS in Rails Apps 30min

05:00PM Networking Event


Track 2:

09:00AM Communicating Over Distance 60min

10:00AM Rails Magic 60min

11:00AM Woah 30min

11:30AM Sit Down and Write 30min

12:00PM Lunch

01:00PM Accounting-Driven Development 45min

01:45PM Clojure Ate Scala (on my project) 45min

02:30PM A World Without HackerNews 30min

03:00PM Ruby on Rails Legacy App Maintenance 60min

04:00PM Rails for Python Developers lightning

05:00PM Networking Event

## Sobre o Projeto

Buscou-se focar mais na estrutura do projeto do que no algoritmo de organização/agendamento das palestras, pois em um projeto bem estruturado realizar adequadas modificações no algoritmo é fácil.

A implementação poderia ser mais simples, sem o uso do Spring, mas aqui pensou-se em permitir a expansão do projeto para uma API REST, onde a partir de uma requisição JSON com as palestras, fosse possível retornar os agendamentos realizados. Da forma como o projeto foi estruturado e implementado, para isto basta acrescentar os endpoints e realizar algumas adaptações para lidar com as requisições e respostas.

A estrutura do projeto também permite que sejam acrescentados novos formatos de input dos dados, que podem ser alterados antes da execução, diretamente no arquivo de propriedades, alterando o valor da propriedade 'event.parser'. Atualmente o único formato suportado é o de arquivo de texto seguindo as regras definidas na descrição do problema (que é o valor atual da propriedade, 'fileEventParser'). Para acrescentar novos formatos basta implementar a interface EventParser, e alterar o valor da propriedade 'event.parser' para o nome do bean desejado.

Para definir a estrutura de tracks e slots, deve-se alterar o arquivo de properties. Atualmente os valores são:

```property
tracks.name=Track 1,Track 2
slots.name=,Lunch,
slots.duration.in.minutes=180,60,240
slots.has.events=true,false,true
slots.start.hour=9,12,13
slots.start.minute=0,0,0
```

Estes valores seguem o solicitado na descrição do problema, onde temos duas trilhas (Track 1 e Track 2), cada uma divida em 3 períodos (chamados no projeto de slots), onde o primeiro e o último não possuem um nome definido, e o do meio é para o almoço (com o nome de 'lunch'). Os tempos dos três slots são 180, 60 e 240 minutos, respectivamente. Cada slot pode suportar palestras/eventos, e isto é configurado pela propriedade 'slots.has.events', onde os valores estão ordenados pela ordem dos slots, ou seja, no exemplo acima apenas lunch não terá palestras. Além disso é possível configurar as horas e minutos do início de cada slot, sempre respeitando a ordem.

Com isto é possível customizar a estrutura da conferência sem a necessidade de alterar o código. A propriedade 'final.slot.name' define o nome do último slot de cada trilha (track), na descrição do problema chamado de Networking. Este slot não possui duração nem horário de início fixo, pois depende do horário de encerramento das palestras para começar.

Algumas outras propriedades são:

```
default.file.full.path=./input/input.txt
output.file.path=./output/conference-tracks.txt
max.track.duration.in.minutes=240
lightning.duration.in.minutes=5
line.pattern=^(.+)\\s(\\d+)?((min)|(lightning))$
```

Onde:

- default.file.full.path - Local do arquivo de entrada de dados
- output.file.path - Local onde será impresso o agendamento das palestras
- max.track.duration.in.minutes - Tempo máximo de duração de uma palestra
- lightning.duration.in.minutes - Tempo de duração de uma lightning
- line.pattern - Expressão regular utilizada para converter cada linha em uma palestra/evento

## Melhoras

- Atualmente as tracks e os slots estão definidos e configurados no arquivo de properties, a ideia é colocá-los em arquivos de metadados, que serão lidos pela aplicação, assim separando das properties
- Centralizar as mensagens de erros
