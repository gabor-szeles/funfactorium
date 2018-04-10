$(document).ready(function () {

    const dom = {

        init: function () {
            console.log("loaded");
            eventApplier.addEventToFilters();
            $("#passwordField, #confirmPasswordField").keyup(events.checkPasswordsMatch);
            $("#userNameRegistrationField").keyup(events.checkUserNameExists);

        }

    };


    const eventApplier = {

        addEventToFilters: function () {
            $(".topic").click(events.filter);
        }

    };


    const events = {

        filter: function (event) {
            let id = $(event.target).attr("id");
            ajax.refreshIndex(id);
        },

        buildFilteredPage: function (response) {
            $(".funfact-data").empty();
            $.each(response, function (i, funfact) {
                $(".funfact-data").append(
                                        `<h3>${funfact.title}</h3>
                                         <h5>${funfact.author}</h5>
                                         <p>${funfact.description}</p>`)
            })
        },

        checkPasswordsMatch: function () {
            let password = $("#passwordField").val();
            let confirmPassword = $("#confirmPasswordField").val();

            if (password != confirmPassword) {
                $("#passwordLabel").html("Passwords do not match!").css("color", "red");
                $("#register-button").prop("disabled", true);
            }else {
                $("#passwordLabel").html("Passwords match.").css("color", "green");
                $("#register-button").prop("disabled", false);
            }
        },

        checkUserNameExists: function () {
            let userName = $("#userNameRegistrationField").val();
            if(userName.length>=5) {
                ajax.checkUserName(userName);
            }
        },

        userNameStatus: function (response) {
            if (response) {
                $("#userNameRegLabel").html("User already exists!").css("color", "red");
                $("#register-button").prop("disabled", true);
            }else {
                $("#userNameRegLabel").html("Username OK!").css("color", "green");
                $("#register-button").prop("disabled", false);
            }

        }
    };


    const ajax = {

        refreshIndex: function (id) {
            $.ajax({
                type: "POST",
                url: "/api/filter",
                dataType: "json",
                data: {"id":id},
                contentType: "application/json",
                success: function (response) {
                    events.buildFilteredPage(response);
                },
                error: function (response) {
                    console.log("refreshIndex error:" + response.responseText);
                }
            });
        },

        checkUserName: function (userName) {
                $.ajax({
                    type: "POST",
                    url: "/api/check-username",
                    dataType: "json",
                    data: {"userName":userName},
                    contentType: "application/json",
                    success: function (response) {
                        events.userNameStatus(response);
                    },
                    error: function (response) {
                        console.log("checkUserName error:" + response.responseText);
                    }
                });
            }

    };

    dom.init();

});
