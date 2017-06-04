(function ($) {

    $.fn.control = function () {

        var $this = this;

        var sendCommand = function(cmd)
        {
            $this.animate({ opacity: 0.25 });
            $this.find('input[type="button"]').prop('disabled', true);

            var url = '/api/carriage/'+cmd;

            $.ajax({
                url: url,
                type: 'POST',
                dataType: 'json',
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify({}),
                success: function (data) {

                    $this.find('input[type="button"]').prop('disabled', false);
                    $this.animate({ opacity: 1 });
                },
                error: function(){

                    $this.find('input[type="button"]').prop('disabled', false);
                    $this.animate({ opacity: 1 });
                }
            });
        }

        $this.find('input[type="button"]').click(function (e) {

            e.preventDefault();
            e.stopPropagation();

            var command = $(this).attr('data-cmd');

            if(command)
            {
                sendCommand(command);
            }

            return false;
        });
    }

})(jQuery);