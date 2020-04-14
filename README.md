# account-organizer

## Java / Spring / JPA

The application structurizes accounts in given JSON. (Allegro recruitment task)

## Structure
![UML](https://github.com/TheTerabit/account-organizer/blob/master/account_organizer_uml.png)

## Input:
| Request type | Body | URL | Description |
| --- | --- | --- | --- |
| ```POST``` | JSON array | https://account-organizer.herokuapp.com/ | Insert multiple accounts. |
| ```POST``` | JSON | https://account-organizer.herokuapp.com/new | Insert new account. |
| ```GET``` | | https://account-organizer.herokuapp.com/ | List the results. |

### Here is an example of building new structure of accounts:

Two accounts are created: a new business account for ```Jan``` and an account for his company (id and login were randomly generated for ```Kowalstwo.com``` account, email is not assigned too).
```
{
    "name": "Jan",
    "surname": "Kowalski",
    "nip": "1234567890",
    "companyName": "Kowalstwo.com",
    "email": "jan.kowalski@gmail.com",
    "phoneNumber1": "123456789",
    "phoneNumber2": "987654321",
    "login": "janprofesorek",
    "id": "000001",
    "address": "ul. Zielona 2/4, 00-123 Warszawa"
}
```

Account created for ```Paweł```. This account is referenced to the previously created ```Kowalstwo.com's``` account. Notice different NIP types.
```
{
    "name": "Paweł",
    "surname": "Korbaczyński",
    "nip": "123-456-78-90",
    "companyName": "Kowalstwo.com",
    "email": "pawel.korba@gmail.com",
    "phoneNumber1": "123456789",
    "phoneNumber2": "987654321",
    "login": "maluch126p",
    "id": "000002",
    "address": "ul. Długa 3/6, 61-123 Poznań"
}
```

```Paweł``` updated his login. The account is found by its ID.
```
{
    "name": "Paweł",
    "surname": "Korbaczyński",
    "nip": "123-456-789-0",
    "companyName": "Kowalstwo.com",
    "email": "pawel.korba@gmail.com",
    "phoneNumber1": "123456789",
    "phoneNumber2": "987654321",
    "login": "polonez",
    "id": "000002",
    "address": "ul. Długa 3/6, 61-123 Poznań"
}
```

There is no NIP in this request: new private user account created for ```Bartol``` and a parent user account similar to previously created company account. Each private user is identified by his name, surname and address.
```
{
    "name": "Bartol",
    "surname": "Omeo",
    "email": "bartol.omeo@gmail.com",
    "phoneNumber1": "123456789",
    "phoneNumber2": "987654321",
    "login": "AceOfSpades",
    "id": "000003",
    "address": "ul. Czerwona 4/8, 85-123 Bydgoszcz"
}
```

Another private account created for ```Bartol``` (different login, ID and email).
```
{
    "name": "Bartol",
    "surname": "Omeo",
    "email": "bartek.switaj@gmail.com",
    "phoneNumber1": "111111111",
    "phoneNumber2": "222222222",
    "login": "JavaDev",
    "id": "000004",
    "address": "ul. Czerwona 4/8, 85-123 Bydgoszcz"
}
```

Duplicated account without ID. Nothing happens.
```
{
    "name": "Bartol",
    "surname": "Omeo",
    "email": "bartek.switaj@gmail.com",
    "phoneNumber1": "111111111",
    "phoneNumber2": "222222222",
    "login": "JavaDev",
    "address": "ul. Czerwona 4/8, 85-123 Bydgoszcz"
}
```

New company and company account. It 'steals' email from ```Jan Kowalski``` (now jan.kowalski@gmail.com is assinged to ```Andrzej``` as current email and to ```Jan Kowalski``` as previous email).
```
{
    "name": "Andrzej",
    "surname": "Pokorowiec",
    "nip": "321-687-21-63",
    "companyName": "FM",
    "email": "jan.kowalski@gmail.com",
    "phoneNumber1": "123456789",
    "phoneNumber2": "987654321",
    "login": "Andrut123",
    "id": "000006",
    "address": "ul. Niebieska 2/4, 00-123 Warszawa"
}
```

## Output:

```
[
    {
        "login": "jnmrj2",
        "id": "jnmrj2",
        "email": null,
        "previousEmail": null,
        "detailedAccount": {
            "login": "jnmrj2",
            "name": "Bartol",
            "surname": "Omeo",
            "address": "ul. Czerwona 4/8, 85-123 Bydgoszcz",
            "privateAccounts": [
                {
                    "login": "JavaDev",
                    "phoneNumber1": "111111111",
                    "phoneNumber2": "222222222",
                    "privateUserLogin": "jnmrj2"
                },
                {
                    "login": "AceOfSpades",
                    "phoneNumber1": "123456789",
                    "phoneNumber2": "987654321",
                    "privateUserLogin": "jnmrj2"
                }
            ]
        }
    },
    {
        "login": "JavaDev",
        "id": "000004",
        "email": "bartek.switaj@gmail.com",
        "previousEmail": null,
        "detailedAccount": {
            "login": "JavaDev",
            "phoneNumber1": "111111111",
            "phoneNumber2": "222222222",
            "privateUserLogin": "jnmrj2"
        }
    },
    {
        "login": "H00b0b",
        "id": "H00b0b",
        "email": null,
        "previousEmail": null,
        "detailedAccount": {
            "login": "H00b0b",
            "nip": "1234567890",
            "name": "Kowalstwo.com",
            "companyAccounts": [
                {
                    "login": "janprofesorek",
                    "name": "Jan",
                    "surname": "Kowalski",
                    "phoneNumber1": "123456789",
                    "phoneNumber2": "987654321",
                    "address": "ul. Zielona 2/4, 00-123 Warszawa",
                    "companyLogin": "H00b0b"
                },
                {
                    "login": "polonez",
                    "name": "Paweł",
                    "surname": "Korbaczyński",
                    "phoneNumber1": "123456789",
                    "phoneNumber2": "987654321",
                    "address": "ul. Długa 3/6, 61-123 Poznań",
                    "companyLogin": "H00b0b"
                }
            ]
        }
    },
    {
        "login": "janprofesorek",
        "id": "000001",
        "email": null,
        "previousEmail": "jan.kowalski@gmail.com",
        "detailedAccount": {
            "login": "janprofesorek",
            "name": "Jan",
            "surname": "Kowalski",
            "phoneNumber1": "123456789",
            "phoneNumber2": "987654321",
            "address": "ul. Zielona 2/4, 00-123 Warszawa",
            "companyLogin": "H00b0b"
        }
    },
    {
        "login": "AceOfSpades",
        "id": "000003",
        "email": "bartol.omeo@gmail.com",
        "previousEmail": null,
        "detailedAccount": {
            "login": "AceOfSpades",
            "phoneNumber1": "123456789",
            "phoneNumber2": "987654321",
            "privateUserLogin": "jnmrj2"
        }
    },
    {
        "login": "D44JIr",
        "id": "D44JIr",
        "email": null,
        "previousEmail": null,
        "detailedAccount": {
            "login": "D44JIr",
            "nip": "3216872163",
            "name": "FM",
            "companyAccounts": [
                {
                    "login": "Andrut123",
                    "name": "Andrzej",
                    "surname": "Pokorowiec",
                    "phoneNumber1": "123456789",
                    "phoneNumber2": "987654321",
                    "address": "ul. Niebieska 2/4, 00-123 Warszawa",
                    "companyLogin": "D44JIr"
                }
            ]
        }
    },
    {
        "login": "Andrut123",
        "id": "000006",
        "email": "jan.kowalski@gmail.com",
        "previousEmail": null,
        "detailedAccount": {
            "login": "Andrut123",
            "name": "Andrzej",
            "surname": "Pokorowiec",
            "phoneNumber1": "123456789",
            "phoneNumber2": "987654321",
            "address": "ul. Niebieska 2/4, 00-123 Warszawa",
            "companyLogin": "D44JIr"
        }
    },
    {
        "login": "polonez",
        "id": "000002",
        "email": "pawel.korba@gmail.com",
        "previousEmail": null,
        "detailedAccount": {
            "login": "polonez",
            "name": "Paweł",
            "surname": "Korbaczyński",
            "phoneNumber1": "123456789",
            "phoneNumber2": "987654321",
            "address": "ul. Długa 3/6, 61-123 Poznań",
            "companyLogin": "H00b0b"
        }
    }
]
```

