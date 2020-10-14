module nts.uk.at.kaf020.a {

    @bean()
    export class Kaf020AViewModel extends ko.ViewModel{
        constructor(props: any) {
            super();
        }

        created(){

        }

         mounted(){
             $("#fixed-table").ntsFixedTable({height: 480, width: 1200});
             setTimeout(() => {
                 $("#pg-name").text("KAF020A " + nts.uk.resource.getText("KAF020_1"));
             }, 300);
         }


         detail (){
            const vm = this;
            vm.$jump('../b/index.xhtml');
         }
    }
}