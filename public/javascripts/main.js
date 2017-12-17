function main(){

    setInterval(myFunction, 1000);
}

myFunction = function call(){
    $.get("/jsonorders", function(data, status){
        dataTreatment(data);
    });
}

closeAllOrders = function call(){
    $.get("/jsonorders/closeAll", function(data, status){
        //dataTreatment(data);
    });
}

dataTreatment = function(objects){

    var tableObject = $("#tableOrders")[0];
    var numTr = $("#tableOrders tr").length;

    if(numTr > objects.length){
        tableObject.innerHTML = "";
    }

    for (var i = 0; i < objects.length; i++) {
        var actual = objects[i];
        var trObjectId = "#trtableNumber" + actual.tableNumber;
        var tdObjectId = ".tdtableNumber" + actual.tableNumber;

        var trObject = $(trObjectId)[0];
        var tdObject = $(tdObjectId);

        var unattendedSeconds = Math.round(actual.timeUntilNow / 1000);
        var unattendedMins = Math.floor(unattendedSeconds / 60);
        var unattendedHours = Math.floor(unattendedMins / 60);

        var hours = unattendedHours;
        var mins = isNaN(unattendedMins%60) ? 0 : unattendedMins%60;
        var secs = isNaN(unattendedSeconds%60) ? 0 : unattendedSeconds%60;

        var hoursS = (hours < 10) ? "0" + hours : "" + hours;
        var minsS = (mins < 10) ? "0" + mins : "" + mins;
        var secsS = (secs < 10) ? "0" + secs : "" + secs;

        var unattendedTime = hoursS + ":" + minsS + ":" + secsS;

        if(tdObject.length == 0){
            trObject = document.createElement("tr");
            trObject.id = "trtableNumber" + actual.tableNumber;

            var actionContent = "<a href=\"/orders/close/" + actual.tableNumber + "\">"+
                "<img src=\"https://image.flaticon.com/icons/svg/148/148766.svg\" style=\"width: 20px; height: 20px\">"+
                "</a>";

            tdObjectId = "tdtableNumber" + actual.tableNumber;

            var tdTableNumber = document.createElement("td");
            tdTableNumber.classList.add(tdObjectId);
            tdTableNumber.id = "tableNumber" + actual.tableNumber;
            tdTableNumber.innerHTML = actual.tableNumber;
            var tdCommand = document.createElement("td");
            tdCommand.classList.add(tdObjectId);
            tdCommand.id = "tableNumber" + actual.tableNumber;
            tdCommand.innerHTML = actual.command;
            var tdTime = document.createElement("td");
            tdTime.classList.add(tdObjectId);
            tdTime.id = "tableNumber" + actual.tableNumber;
            tdTime.innerHTML = unattendedTime;
            var tdAction = document.createElement("td");
            tdAction.classList.add(tdObjectId);
            tdAction.id = "tableNumber" + actual.tableNumber;
            tdAction.innerHTML = actionContent;

            trObject.appendChild(tdTableNumber);
            trObject.appendChild(tdCommand);
            trObject.appendChild(tdTime);
            trObject.appendChild(tdAction);

            tableObject.append(trObject);
        }
        else{            
            tdObject[0].innerHTML = actual.tableNumber;
            tdObject[1].innerHTML = actual.command;
            tdObject[2].innerHTML = unattendedTime;
        }

        if(unattendedSeconds >= 5*60){
            trObject.classList.add("danger");
        }
        else if(unattendedSeconds >= 2.5*60){
            trObject.classList.add("warning");
        }
        else if(unattendedSeconds >= 60){
            trObject.classList.add("success");
        }
    }
}