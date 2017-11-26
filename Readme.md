Написать мини клиент hh.ru, состоящий из двух экранов.
Первый экран состоит из поля ввода и списка.
После ввода ключевого слова в списке отображаются вакансии, соответствующие этому ключевому слову. Также присутствует возможностью обновления списка вакансий с помощью паттерна pull-to-refresh. По нажатию на элемент списка вакансий открывается второй экран с описанием данной вакансии.

Требования

- [x] Использовать наше API, описание доступно на https://dev.hh.ru/
- [x]  Приложение должно работать на версиях Android от 4.0 и выше. Для Target Api использовать последнюю доступную версию.
- [ ]  По возможности следовать Android material guidelines https://material.io/guidelines/material-design/introduction.html (отступы в списках, расстояния между текстом и т. п.)
- [x]  Необходимо добавить кэширование данных, и в случае, если последующий запуск приложения происходит без интернета, выдавать введенные в предыдущий раз  данные
- [ ]  В приложении должна быть понятная для пользователя обработка особых ситуаций, например если список вакансий пуст, если нет интернета.
- [ ]  Необходимо добавить поддержку планшетов. В альбомном режиме для планшетов, экран со списком вакансий и экран описания выбранной вакансии должны быть реализованы одним экраном (в качестве примера можно посмотреть на android приложение gmail).