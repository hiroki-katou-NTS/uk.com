module nts.uk.at.view.kanr002.a.viewmodel {
    

    @bean()
    class Knr002AViewModel extends ko.ViewModel {

        displaytext: String = 'hoi cham';

        constructor(params: any) {
            super();
        }
    
        created(params: any) {
            console.log('started');
        }
    
        mounted() {
            // raise event when view initial success full
        }

        
    }

}