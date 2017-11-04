var delay = (function(){
    var timer = 0;
    return function(callback, ms){
        clearTimeout (timer);
        timer = setTimeout(callback, ms);
    };
})();

$("#searchPoisson").keyup(function(){
    delay(function(){
        var q = $("#searchPoisson").val();
        if(q.length >= 3){ // run search only when at least 3 characters are typed
            $.getJSON(
                "/poisson/findByNom?nom=" + q,
                function(data) {
                var $results = $("#search-results");
                    $results.empty();
                    $results.append("<ul>");

                    console.log("data = ", data);

                    $.each(data, function(i, poisson){
                        $results.append("<li><a href='/poisson/" + poisson.code + "'>" + poisson.espece + "</a></li>");
                    });
                    $results.append("</ul>");
                }
            );
        }
    }, 500 );
});