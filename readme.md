<h2>Implementation description</h2>
<div>
    Calculation is done by acquiring the "Trip" object in the HTTP POST requests body. A trip
consists of :
<ol>
    <li>Start (The trip start city, for example "Riga")</li>
    <li>Destination (The trip destination city, for example "Vilnius")</li>
    <li>And a list of passenger objects, which consist of :
        <ul>
            <li>List of passengers luggage objects, which have a defined type of (BAG) for now</li>
            <li>And passenger type (CHILD OR ADULT)</li>
        </ul>
    </li>
</ol>

The objects that have calculation involved in them have a specific "type" field which
points to how much of the base price they should include in the main calculation.

Though PassengerType and LuggageType are calculated the same way (via the Discountable interface),
I decided to keep them separate for readability.

When the calculation is done the result object is "CalculationResult" which consists of:
<ol>
    <li>The total amount of money needed to be paid</li>
    <li>A list of subtotal objects which consist of:
        <ul>
            <li>PassengerType [ADULT,CHILD]</li>
            <li>The passengers ticket price (not counting luggage)</li>
            <li>A list of Luggage subtotals,
            which have the amount needed to pay for that specific type of luggage as well as the luggage type</li>
        </ul>
</li>
</ol>

<h3>Various design decisions</h3>

I decided to create enums that would dictate the % of the price used in the calculations (PersonType, LuggageType),
instead of implementations of abstract classes because the only real difference between these
calculations is a single number, which I felt was  not large enough of a difference to warrant creating
numerous classes.

I have left the BigDecimal amounts not rounded on purpose as I feel that giving the largest
amount of precision to the people using these service would be best.

Also i decided not to use the same objects (in this case "Trip") to store the calculation results in them
,as it could happen, in a theoretical future, that the frontend Trip object could have gone through numerous
changes which could impact the calculation in unexpected ways if calculation is done in the same object,
for this reason i decided to have a separate CalculationResult even though the fields used in Trip and CalculationResult match quite a bit.
</div>

<h2>Curl command for testing endpoint</h2>
<div>
curl --location --request POST 'localhost:8080/calculate-ticket-price' \
--header 'Content-Type: application/json' \
--data-raw '{
    "start": "Riga",
    "destination": "Vilnius",
    "passengers": [
        {
            "luggage": [
                {
                    "type": "BAG"
                },
                {
                    "type": "BAG"
                }
            ],
            "passengerType": "ADULT"
        },
        {
            "luggage": [
                {
                    "type": "BAG"
                }
            ],
            "passengerType": "CHILD"
        }
    ]
}'
</div>