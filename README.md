# account-organizer

## Java / Spring / JPA

The application structurizes accounts in given JSON.

### Input:

```
[
{
    "name": "Jan",
    "surname": "Kowalski",
    "nip": "1234567890",
    "companyName": "Kowalstwo.com",
    "email": "jan.kowalski@gmail.com",
    "phoneNumber1": "123456789",
    "phoneNumber2": "987654321",
    "login": "janulinek",
    "id": "000001",
    "address": "ul. Zielona 2/4, 00-123 Warszawa"
},
{
    "name": "Paweł",
    "surname": "Korbaczyński",
    "nip": "1234567890",
    "companyName": "Kowalstwo.com",
    "email": "pawel.korba@gmail.com",
    "phoneNumber1": "123456789",
    "phoneNumber2": "987654321",
    "login": "maluch126p",
    "id": "000002",
    "address": "ul. Długa 3/6, 61-123 Poznań"
},
{
    "name": "Bartol",
    "surname": "Omeo",
    "email": "bartol.omeo@gmail.com",
    "phoneNumber1": "123456789",
    "phoneNumber2": "987654321",
    "login": "KingOfSpades",
    "id": "000003",
    "address": "ul. Ceglana 4/8, 85-123 Bydgoszcz"
},
{
    "name": "Bartol",
    "surname": "Omeo",
    "email": "bartek.switaj@gmail.com",
    "phoneNumber1": "111111111",
    "phoneNumber2": "222222222",
    "login": "JavaDev",
    "id": "000004",
    "address": "ul. Ceglana 4/8, 85-123 Bydgoszcz"
}
]
```

### Output:

```
[
    {
        "login": "KUVJ5X",
        "id": "KUVJ5X",
        "email": "jan.kowalski@gmail.com",
        "detailedAccount": {
            "login": "KUVJ5X",
            "nip": "1234567890",
            "name": "Kowalstwo.com",
            "companyAccounts": [
                {
                    "login": "janulinek",
                    "name": "Jan",
                    "surname": "Kowalski",
                    "phoneNumber1": "123456789",
                    "phoneNumber2": "987654321",
                    "address": "ul. Zielona 2/4, 00-123 Warszawa",
                    "companyLogin": "KUVJ5X"
                },
                {
                    "login": "maluch126p",
                    "name": "Paweł",
                    "surname": "Korbaczyński",
                    "phoneNumber1": "123456789",
                    "phoneNumber2": "987654321",
                    "address": "ul. Długa 3/6, 61-123 Poznań",
                    "companyLogin": "KUVJ5X"
                }
            ]
        }
    },
    {
        "login": "janulinek",
        "id": "000001",
        "email": "jan.kowalski@gmail.com",
        "detailedAccount": {
            "login": "janulinek",
            "name": "Jan",
            "surname": "Kowalski",
            "phoneNumber1": "123456789",
            "phoneNumber2": "987654321",
            "address": "ul. Zielona 2/4, 00-123 Warszawa",
            "companyLogin": "KUVJ5X"
        }
    },
    {
        "login": "maluch126p",
        "id": "000002",
        "email": "pawel.korba@gmail.com",
        "detailedAccount": {
            "login": "maluch126p",
            "name": "Paweł",
            "surname": "Korbaczyński",
            "phoneNumber1": "123456789",
            "phoneNumber2": "987654321",
            "address": "ul. Długa 3/6, 61-123 Poznań",
            "companyLogin": "KUVJ5X"
        }
    },
    {
        "login": "xJF17p",
        "id": "xJF17p",
        "email": "bartol.omeo@gmail.com",
        "detailedAccount": {
            "login": "xJF17p",
            "name": "Bartol",
            "surname": "Omeo",
            "address": "ul. Ceglana 4/8, 85-123 Bydgoszcz",
            "privateAccounts": [
                {
                    "login": "KingOfSpades",
                    "phoneNumber1": "123456789",
                    "phoneNumber2": "987654321",
                    "privateUserLogin": "xJF17p"
                },
                {
                    "login": "JavaDev",
                    "phoneNumber1": "111111111",
                    "phoneNumber2": "222222222",
                    "privateUserLogin": "xJF17p"
                }
            ]
        }
    },
    {
        "login": "KingOfSpades",
        "id": "000003",
        "email": "bartol.omeo@gmail.com",
        "detailedAccount": {
            "login": "KingOfSpades",
            "phoneNumber1": "123456789",
            "phoneNumber2": "987654321",
            "privateUserLogin": "xJF17p"
        }
    },
    {
        "login": "JavaDev",
        "id": "000004",
        "email": "bartek.switaj@gmail.com",
        "detailedAccount": {
            "login": "JavaDev",
            "phoneNumber1": "111111111",
            "phoneNumber2": "222222222",
            "privateUserLogin": "xJF17p"
        }
    }
]
```

