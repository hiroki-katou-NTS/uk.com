module nts.uk.at.view.kmk002.a {
    __viewContext.ready(function() {
        var screenModel = __viewContext.vm = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            // focus optional item name input
            $('#inpName').focus();
            var isScrollCommand = false;
            $(document).delegate(".combo-table", "igcombodropdownopening", function(evt) {
                var elem = this;
                var startView = 0;
                var endView = $('.nts-fixed-body-container').width();
                var startElem = $(elem).offset().left - $('.nts-fixed-body-container').offset().left;
                var endElem = startElem + $(elem).width();
                var isVisible = ((endElem <= endView) && (startElem >= startView));
                var scrollSize = 0;
                if (endElem > endView) {
                    scrollSize = endElem - endView;
                }
                if (startElem < startView) {
                    scrollSize = startElem - startView
                }
                if (!isVisible) {
                    var currentPositionScroll = $('.nts-fixed-body-container').scrollLeft();
                    isScrollCommand = true;
                    $('.nts-fixed-body-container').scrollLeft(currentPositionScroll + scrollSize);
                }
            });
            $('.nts-fixed-body-container').on('scroll', function() {
                if (!isScrollCommand) {
                    $(".combo-table").igCombo("option", "animationHideDuration", 0);
                    $(".combo-table").igCombo("closeDropDown");
                }
                isScrollCommand = false;
            })
        });
    });
}