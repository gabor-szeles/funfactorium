$(document).ready(function () {

    const dom = {

        init: function () {
            console.log("loaded")
            $("#addFactTopics").multiselect({
                dropRight: true,
                enableFiltering: true
            });
            $("#submitFunFact").click(events.submitFunFact);
        }

    };

    const events = {

        submitFunFact: function (event) {
            event.preventDefault();
            let formFilled = events.checkFormFilled();
            console.log(formFilled)
            if(formFilled) {
                let jsonFormData = {"title": $("#addTitle").val(),
                                    "description": $("#addDescription").val(),
                                    "topics": $("#addFactTopics").val()};
                ajax.submitFunfactData(jsonFormData);
            }
        },

        checkFormFilled: function () {
            let missing = 0;
            $("#titleLabel").html("Title").css("color", "black");
            $("#descriptionLabel").html("Description").css("color", "black");
            $("#topicLabel").html("Topics").css("color", "black");
            if($("#addTitle").val()==="") {
                $("#titleLabel").html("Please add a title!").css("color", "red");
                missing++;
            }
            if($("#addDescription").val()==="") {
                $("#descriptionLabel").html("Please add the description!").css("color", "red");
                missing++;
            }
            if($("#addFactTopics").val().length===0) {
                $("#topicLabel").html("Please choose at least one topic!").css("color", "red");
                missing++;
            }
            return missing <= 0;
        }

    };

    const ajax = {

        submitFunfactData: function (json) {
            $.ajax({
                type: "POST",
                url: "/api/add_funfact",
                data: JSON.stringify(json),
                contentType: "application/json",
                success: function () {
                    window.location.replace("/");
                },
                error: function (response) {
                    $("#titleLabel").html("Title already in database!").css("color", "red");
                    console.log("log "+response.responseText);
                }
            });
        }
    };

    dom.init();
});