$(document).ready(function () {

    const dom = {

        init: function () {
            console.log("loaded");
            eventApplier.addEventToFilters();
        }

    };


    const eventApplier = {

        addEventToFilters: function () {
            $(".topic").click(events.filter);
        },
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
        }

    };


    dom.init();

});
