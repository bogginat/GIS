Проект "Сбор, анализ и и публикация геоданных"
------------------------------------------------------------------------------------------------------------------------------


Выполняет:

Богданова Наталья Олеговна

группа 154

Цель проекта:
------------------------------------------------------------------------------------------------------------------------------


Основной задачей является создание приложения для платформы Android. 
Концепция: Приложение представляет собой обучающую программу, которая помогает познавать определенные части города и
его достопримечательности. 

Обучение реализуется в виде теста, проходя который ученик запоминает информацию, анализируя свои
ошибки. При запуске приложения пользователь видит карту, разделенную на области, означающие части города, доступные для
изучения. Пользователь выбирает интересующую его часть, и открывается тестирование. Тестирование представляет собой викторину:
на экране появляется фотография, отображающая какую-то достопримечательность из выбранной чсти города. Пользователь нажимает на
предполагаемое место расположения показанного объекта. Если место выбрано корректно, ответ засчитывается и показывается новая
фотография. Если нет, то пользователю сообщается, что он неправ, показывается верное расположение и только потом задается новый
вопрос. По результатам прохождения тестирования части города окрашиваются в цвета в зависимости от того, насколько хорошо
пользователь знает эту часть города. 

Приложение направлено на развитие памяти, а также помогает туристу разобраться в городе, который он выбрал для посещения.
Приложение может помочь приезжим на новое место людям научиться ориентироваться на местности или помочь интернет туристу лучше
запомнить информацию.


Используемые технологии:
------------------------------------------------------------------------------------------------------------------------------


Python, JavaScript, HTML - технологии, используемые для создания начальной упрощенной модели программы: веб-приложения.
Довольно-таки просты для изучения и хорошо подходят для создания веб-приложений.

GeoJSON, Google Maps API, NextGIS - изпользуются для сбора, обработки и хранения геоданных. Самые удобные для работы
технологии, так как существует множество типовых алгоритмов и решений, большой объем документации. Легко изучаются
самостоятельно. Имеется множетсво готовых библиотек и функций, содержащих данные и алгоритмы для работы моего приложения.

Android Studio, Java - технология, используемая для создания и функционирования приложения. Самое удобное для меня из
существующих, так как поддерживает множество версий Android и позволяет использовать шаблонный функционал для приложений.


План работы:
------------------------------------------------------------------------------------------------------------------------------


1) Создание веб-карты (Сделать веб-страничку, на которой отображена гугл-карта с центром в вашем любимом городе мира и соответствующим масштабом, чтобы захватить основную часть города. Рядом с картой - текстовое поле для вывода данных. По клику в любой точке карты меняется масштаб, центр карты переносится в точку, на которую кликнули, и в текстовом поле выводятся: координаты точки, список достопримечательностей в её окрестности.)

2) Cоздание Android приложения с картой. (Карта разделена на равных 9 частей, окрашенных в разные цвета, каждая из которых отображает определенную часть города. По клику происходит переход в определенную часть города.)

3) Сделать вход в приложение с помощью аккаунта Google. Реализовать возможность привязки фотографий к местам, создать общую базу фотографий.

4) Создание самой викторины с вопросами и ответами. Программа должна говорить о правильности ответа. Все данные ответов сохраняются в зависимости от аккаунта. 

5) Реализация возможности смены карты текущего города на другой город из базы с соответствующим изменением викторины.

6) Создание уровней для городов(по сложности) и поочередного прохождения каждого уровня. Каждый уровень имеет свою ценность и пользователь получает определенный бонусы за его прохождения. (Предполагается, что бонусы - Фишка главной(самой известной) достопримечательности города, которая сохраняется в коллекцию игрока.)

7) Реализация общей таблицы пользователей с рейтингом и соревновательной возможностью.

!!! Пункты 1-4 должны быть сделаны к Контрольной точке 2.



Контрольная точка 2.
------------------------------------------------------------------------------------------------------------------------------

1) Архитектура:
- 3 активити: вход через google аккаунт(Signin.java), общая карта всех полигонов(MapsActivivty.java), викторина(NE.java)
- 4 основных фрагмента для викторины, которые динамически сменяют друг друга внутри контейнеров: карта для выбора точки(MapViewFragment.java), карта с правильным ответом(AnswerMapFragment.java), фотография места(PhotoView.java), текстовый вопрос и текстовый вывод результата(QuizFragment.java)
- для всех карт использованы методы Google Maps Api, как для активити, так и для фрагментов
- вход производится с использованием OAuth 2.0 client ID, таким образом можно сохранять пользователя в системе и с легкостью хранить данные о пользователях; используются основные методы работы с GoogleApiClient
- для разделения основной карты на области используется работа с полигонами
- активно используются методы передачи информации между активити и фрагметом (Bundle, interface Listener)для реализации передачи координат точки, названии места, корректной связи изменений внутри каждого фрагмента активити
- для поиска соседних мест используется работа с Google Places Api, а также активная работа с JSON
- для поиска используется Google Places Api и Google Photo (после обработки приходящего json удается вычленить интересующие данные и оперировать ими для корректной работы карт и для поиска нужной фотографии)
- для отображения фотографии использованы методы Picasso, которые удобны в данном случае, так как не приходится делать базу данных из фотографий, а достаточно знать URL адрес фотографии места(который можно получить по особому ключу, извлекаемую из объекта JSON, возвращаемого в результате запроса к данным Google Places)
- класс AppConfig - вспомогательный класс для работы с JSON объектом, возвращенным по запросу
- класс AppController - вспомогательный класс для работы с запросом и принятием данных(реалтзован с помощью библиотеки Volley)
- для каждого метода написан layout, корректно отображающий данные и соответствующий конкретному классу
- для каждого объекта прописываются все методы, необходимые для корректной работы и реализующие жизненный цикл объекта
2) Инструкция по компиляции:
- Необходима установка Android Studio(https://developer.android.com/studio/index.html)
- При запуске приложения и после загрузки основных библиотек(установка которых описана в официальном руководстве по Android Studio) будут предложены для скачивания необходимые библиотеки, которых нет в основном пакете. Необходимо следовать инструкциям по их установке.
- Приложение запускается через эмулятор с API не ниже 21
- Если необходим запуск с телефона, воспользуйтесь сбором приложения в Android Studio(http://ru.stackoverflow.com/questions/434251/%D0%9A%D0%B0%D0%BA-%D0%B2-android-studio-%D1%81%D0%BE%D0%B7%D0%B4%D0%B0%D0%B2%D0%B0%D1%82%D1%8C-%D1%83%D1%81%D1%82%D0%B0%D0%BD%D0%BE%D0%B2%D0%BE%D1%87%D0%BD%D1%8B%D0%B9-apk-%D1%84%D0%B0%D0%B9%D0%BB)
3) Описание функциональности:
- Проект на данно этапе имеет 3 основных активити и несколько фрагментов. Первая активити - это вход через google аккаунт, при нажатии кнопки "Начать".
- Если вход произошел успешно, то открывается вторая активити - главный экран с выбором полигонов(области на карте, окрашенные в разные цвета/разделение на зоны). Пользователь выбирает интересующую часть города кликом по полигону, по клику камера переносится на выбранный полигон, появляется кнопка "Начать", по нажатию которой стартует новая активити и начинается викторина. При неуспешном входе пользователь остается на стартовом экране и может попробовать войти снова.
- Пользователь видит перед собой 3 кнопки в верхнем ряду: "Карта", "Дальше" (есть еще временно скрытая кнопка "Ответ") и "Назад". На экране отображен вопрос в текстовом виде, который спрашивает о местоположении определенного объекта, чуть ниже выведена фотография данного объекта. Пользователь нажимает на "Карта", отображается карта, по которой он делает клик (по верной или неверной точке). Викторина выводит сообщение о верности или неверности ответа, появляется кнопка "Ответ", по клико по которой отображается карта с маркером на месте, в котором разполагается запрошенный ранее объект. Нажав кнопку далее, пользователь может перейти к новому вопросу. Нажав кнопку "Назад", пользователь позвращается к главному экрану.
- Пользователь, вернувшись на главный экран, может нажать кнопку "Выйти", тогда он будет перенесен на стартовую активити, где при нажатии кнопки "Выйти" будет произведен выход из google аккаунта. 
