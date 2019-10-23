import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    route: '/ccg/007/a',
    template: require('./index.html'),
    name: 'contractAuthentication'
})
export class ContractAuthenticationComponent extends Vue {
}