import { _, Vue } from '@app/provider';
import { component, Prop, Watch } from '@app/core/component';

@component({
    name: 'cdls02a',
    template: require('./index.vue'),
    style: require('./style.scss'),
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
        this.data = _.filter(this.allData, (data) => data.closureId == closureId || data.closureId == 'No');
        this.searchText = '';
    }

    public created() {
        let self = this;
        if (self.params.isDisplayClosureSelection) {
            // Find all closure in current month.
            self.$http.post('at', servicePath.findAllClosureItems).then((result: any) => {
                self.closureList = _.sortBy(result.data, (item) => item.id);

                if (self.params.isShowNoSelectRow) {
                    self.allData.push({
                        code: '',
                        name: 'CDLS02_6',
                        closureId: 'No'
                    });
                }

                this.findEmployments();

            }).catch((res: any) => {
                this.$modal.error(res.messageId).then(() => self.$close());
            });
        } else {
            self.$http.post('com', servicePath.findAllEmployments).then((result: any) => {
                if (_.isEmpty(result.data)) {
                    this.$modal.error({ messageId: 'Msg_1566', messageParams: ['Com_Employment'] }).then(() => self.$close());
                } else {
                    if (self.params.isShowNoSelectRow) {
                        result.data.push({
                            code: '',
                            name: 'CDLS02_6'
                        });
                    }
                    self.allData = _.sortBy(result.data, ['code']);
                    self.data = self.allData;
                    let findData = _.find(self.allData, (data) => data.code == self.params.selectedCode);
                    if (findData == undefined) {
                        self.activeNoSelect = true;
                    } else {
                        self.activeNoSelect = false;
                    }

                }
                this.$mask('hide');
            }).catch((res: any) => {
                this.$modal.error(res.messageId).then(() => self.$close());
            });
        }

    }

    public mounted() {
        this.$mask('show', { message: true });
    }

    public findEmployments() {
        let self = this;
        self.$http.post('at', servicePath.findEmpByClosureIdMob).then((result1: any) => {
            if (!_.isEmpty(result1.data)) {
                // Find by employment codes.
                let codes = [];
                let closureIds = [];
                _.forEach(result1.data, (item) => {
                    codes.push(item.employmentCD);
                    closureIds.push(item.closureId);
                });
                self.$http.post('com', servicePath.findEmploymentByCodes, codes).then((result2: any) => {
                    result2.data = _.sortBy(result2.data, ['code']);
                    _.forEach(result2.data, (item2) => {
                        self.allData.push({
                            code: item2.code,
                            name: item2.name,
                            closureId: _.find(result1.data, (item1) => item1.employmentCD == item2.code).closureId
                        });
                    });

                    self.closureList = _.filter(self.closureList, (item) => _.includes(closureIds, item.id));

                    let findData = _.find(self.allData, (data) => data.code == self.params.selectedCode);
                    if (findData == undefined) {
                        self.activeNoSelect = true;
                        self.data = _.filter(self.allData, (data) => data.closureId ==  self.closureList[0].id || data.closureId == 'No');
                        self.selectedClosure = self.closureList[0].id;
                    } else {
                        self.activeNoSelect = false;
                        self.data = _.filter(self.allData, (data) => data.closureId == findData.closureId || data.closureId == 'No');
                        self.selectedClosure = findData.closureId;
                    }
                }).catch((res: any) => {
                    this.$modal.error(res.messageId).then(() => self.$close());
                });
            } else {
                this.$modal.error({ messageId: 'Msg_1566', messageParams: ['Com_Employment'] }).then(() => self.$close());
            }
            this.$mask('hide');
        }).catch((res: any) => {
            this.$modal.error(res.messageId).then(() => self.$close());
        });
    }

    public searchList() {
        this.data = _.filter(this.allData, (data) => data.closureId == this.selectedClosure || data.closureId == 'No');
        let dataSearch = _.filter(this.data, (item) => item.code.indexOf(this.searchText) != -1 || item.name.indexOf(this.searchText) != -1);
        if (dataSearch.length == 0) {
            this.$modal.error('Msg_1313');
        } else {
            this.data = dataSearch;
        }
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
    findEmpByClosureIdMob: 'ctx/at/shared/workrule/closure/findEmpByClosureIdMob/',
    findEmploymentByCodes: 'bs/employee/employment/findByCodes'
};