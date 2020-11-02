streusaApp.controller("deadlinersController", ['$scope', '$http', '$location', '$rootScope', function($scope, $http, $location,$rootScope) {
    console.log("[INFO] Hello World from deadlinersController");

    var host = mainController.getHost();

    $scope.deadlines = [];
    $scope.event = null;

    var calendarEl = null;
    var calendar = null;

    $scope.retrieveAllDeadlines = function(){
        mainController.startProgressIndicator('#loading');
        $http.get(host+"/deadlinesController/api/retrieveAllDeadlines").then(function(response){
            $scope.deadlines = response.data;

            calendarEl = document.getElementById('calendar');

            calendar = new FullCalendar.Calendar(calendarEl, {
                plugins: [ 'interaction', 'dayGrid', 'timeGrid' , 'bootstrap'],
                defaultView: 'timeGridWeek',
                defaultDate: new Date(),
                minTime: '09:00:00',
                maxTime: '21:00:00',
                contentHeight: 'auto',
                allDaySlot: false,
                selectable: true,
                editable: false,
                selectHelper: true,
                header: {
                    left: 'prev,next today',
                    center: 'title',
                    right: 'timeGridDay,timeGridWeek,dayGridMonth'
                },
                eventSources: [
                    {
                        events: $scope.deadlines,
                        color: '#2C3E50',   // an option!
                        textColor: 'white'
                    }
                ]
            });

            calendar.render();
            mainController.stopProgressIndicator('#loading');

            /*calendar.on('dateClick', function(info) {
                console.log(info);
                $scope.event = {title: "", start: info.dateStr, end:""};
                $('#calendarModal').modal('show');

            });*/

            calendar.on('select', function(info){
                console.log(info);
                $scope.newEvent = {title: "", start: info.startStr, end: info.endStr}
                $scope.event = $scope.newEvent;
                $('#calendarModal').modal('show');

            })

            calendar.on('eventClick', function(info) {
                console.log("Event: " +info.event.title );
                console.log("Event: " +info.event.extendedProps._id);
                console.log("Event: " +info.event.extendedProps._rev);
                var end = null;
                if(info.event.end == null){
                    end = ""
                }else{
                    end = info.event.end
                }
                $scope.editEvent = {
                    title: info.event.title,
                    _id: info.event.extendedProps._id,
                    _rev: info.event.extendedProps._rev,
                    start: info.event.start,
                    end: info.event.end
                };

                $('#calendarEditModal').modal('show');
            });

            /*calendar.on('eventMouseEnter', function(info){
                console.log(info);
            })*/

        })
    }

    $scope.retrieveAllDeadlines();

    $scope.refreshAllDeadlines = function(){
        location.href = host + '/deadliners';
    }


    $scope.insertNewEvent = function(newEvent){
        $('#calendarModal').modal('hide');
        newEvent.color = '#2C3E50';  // an option!
        newEvent.textColor= 'white';
        $http.post(host+"/deadlinesController/api/insertDeadline", newEvent).then(function(response){
            alert(response.data.message);
            $scope.refreshAllDeadlines();

        })
    }

    $scope.modifyEvent = function(editDeadline){
        $http.post(host+"/deadlinesController/api/modifyDeadline", editDeadline).then(function(response){
            $('#calendarEditModal').modal('hide');
            alert(response.data.message);
            $scope.refreshAllDeadlines();
        })
    }

    $scope.deleteEvent = function(deleteDeadline){
        $http.post(host+"/deadlinesController/api/deleteDeadline", deleteDeadline).then(function(response){
            $('#calendarEditModal').modal('hide');
            alert(response.data.message);
            $scope.refreshAllDeadlines();
        })
    }

    $scope.deleteEventFromCache = function(){
        $scope.event = null;
        $scope.editEvent = null;
    }


}]);