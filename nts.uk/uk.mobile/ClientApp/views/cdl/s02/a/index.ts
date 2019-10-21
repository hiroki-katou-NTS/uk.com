import { _, Vue } from '@app/provider';
import { component, Prop, Watch } from '@app/core/component';

@component({
    name: 'cdls02a',
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {
        selectedClosure: {
            required: true,
        }
    },
    constraints: []
})
export class CdlS02AComponent extends Vue {
    @Prop({
        default: () => ({
            isDisplayClosureSelection: false,
            isShowNoSelectRow: false,
            selectedCode: null
        })
    })
    public readonly params!: IParameter;
    public closureList: Array<any> = [];
    public selectedClosure: number = null;
    public allData: Array<any> = [];
    public data: Array<any> = [];
    public activeNoSelect: boolean = true;
    public searchText: string = '';

    @Watch('selectedClosure')
    public changeClosure(closureId) {
        this.findEmployments(closureId);
        this.searchText = '';
    }

    public created() {
        let self = this;
        if (self.params.isDisplayClosureSelection) {
            // Find all closure in current month.
            self.$http.post('at', servicePath.findAllClosureItems).then((result: any) => {
                self.closureList = _.sortBy(result.data, (item) => item.id);

                self.selectedClosure = self.closureList[0].id;
                this.findEmployments(self.selectedClosure);
            });
        } else {
            self.$http.post('com', servicePath.findAllEmployments).then((result: any) => {
                if (_.isEmpty(result.data)) {
                    this.$modal.error({ messageId: 'Msg_1566', messageParams: ['Com_Employment'] }).then(() => self.$close());
                } else {
                    if (self.params.isShowNoSelectRow) {
                        result.data.push({
                            code: '',
                            name: this.$i18n('CDLS02_6')
                        });
                    }               
                    self.allData = _.sortBy(result.data,['code']);
                    self.data = self.allData;

                    if (_.find(self.allData, (item) => item.code == self.params.selectedCode) != undefined) {
                        self.activeNoSelect = false;
                    } else {
                        self.activeNoSelect = true;
                    }
                }              
            });
        }
        
    }

    public findEmployments(closureId: number) {
        let self = this;

        self.$http.post('at', servicePath.findEmploymentByClosureId + closureId).then((result: any) => {
            if (!_.isEmpty(result.data)) {
                // Find by employment codes.
                self.$http.post('com', servicePath.findEmploymentByCodes, result.data).then((result: any) => {
                    if (self.params.isShowNoSelectRow) {
                        result.data.push({
                            code: '',
                            name: this.$i18n('CDLS02_6')
                        });
                    }               
                    self.allData = _.sortBy(result.data,['code']);
                    self.data = self.allData;

                    if (_.find(self.allData, (item) => item.code == self.params.selectedCode) != undefined) {
                        self.activeNoSelect = false;
                    } else {
                        self.activeNoSelect = true;
                    }
                });
            } else {
                this.$modal.error({ messageId: 'Msg_1566', messageParams: ['Com_Employment'] }).then(() => self.$close());
            }
        });
    }

    public searchList() {
        this.data = _.filter(this.allData, (item) => item.code.indexOf(this.searchText) != -1 || item.name.indexOf(this.searchText) != -1 );
    }

}
interface IParameter {
    isDisplayClosureSelection: boolean;
    isShowNoSelectRow: boolean;
    selectedCode: string;
}
const servicePath = {
    findAllClosureItems: 'ctx/at/shared/workrule/closure/find/currentyearmonthandused',
    findAllEmployments: 'bs/employee/employment/findAll/',
    findEmploymentByClosureId: 'ctx/at/shared/workrule/closure/findEmpByClosureId/',
    findEmploymentByCodes: 'bs/employee/employment/findByCodes'
};