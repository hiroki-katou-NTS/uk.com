__viewContext.ready(function () {
    class ScreenModel {
        constructor() {
            var self = this;
            $('#buttonA1_2').focus();
        }
        
        opendScreenB() {
            nts.uk.request.jump("/view/kbt/002/b/index.xhtml");
        }

        opendScreenF() {
            nts.uk.request.jump("/view/kbt/002/f/index.xhtml");
        }
    }
    this.bind(new ScreenModel());
});