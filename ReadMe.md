------------------------------------
#Тестовое задание для стажеровки.#
 
MinVersion SDK - 23 

TargetVersion SDK - 29 

------------------------------------
Программа производит парсинг файла из ресурса: 

   http://www.mocky.io/v2/56fa31e0110000f920a72134 

Перед тем как считать данные выполняется проверка на то, подключено ли устройство к сети. 
  
  Обновление данных выполняется при старте приложения либо а также по Swipe (**SwipeRefreshLayout**). На случай, если вдруг при запуске приложения небыл включен интерент.  

Считывание данных производится ассинхронно. 
AsyncTask - реализован в `JsonReader`. 
После того, как считывание и разбор данных завершены генерируется событие `CommandDataAvailiableEvent` через которое результат возвращается в `MainActivity`. 

Для того, чтобы осуществлять запросы из Http в файл манифеста были добавлены разрешения.   

`<uses-permission android:name="android.permission.INTERNET"/>`

`<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>`

`android:networkSecurityConfig="@xml/network_security_config"`
