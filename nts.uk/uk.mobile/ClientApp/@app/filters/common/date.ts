import { date } from '@app/utils';
import { Vue } from '@app/provider';

Vue.filter('date', (d: Date, format: string) => date.format(d, format));