module nts.uk.at.view.kmr002.a.service {
    var paths = {
        startScreen: "at/record/reservation/bento/find",
        register: "at/record/reservation/bento/save",
        update: "at/record/reservation/bento/update"
    }
    
    export function startScreen(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.startScreen, param);
    }
    
    export function register(bentoReservation: any): JQueryPromise<void> {
       return nts.uk.request.ajax("at", paths.register,bentoReservation);
    }
    
    export function update(bentoReservation: any): JQueryPromise<void> {
       return nts.uk.request.ajax("at", paths.update,bentoReservation);
    }
}
