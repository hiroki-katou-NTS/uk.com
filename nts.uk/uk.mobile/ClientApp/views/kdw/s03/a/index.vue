<template>
    <div class="kdw003a">
        <div v-if="displayFormat == '0'">
            <div class="row">
                <div class="col-9">
                    <nts-dropdown v-model="selectedEmployee">
                        <option v-for="item in lstEmployee" :value="item.id">
                            {{item.code}} &nbsp;&nbsp;&nbsp; {{item.businessName}}
                        </option>
                    </nts-dropdown>
                </div>
                <div class="col-3">
                    <button type="button" class="btn btn-primary btn-block" v-on:click="openMenu">{{'KDWS03_23' | i18n}}</button>
                </div>
            </div>
            <div class="row">
                <div class="col-5">
                    <nts-year-month v-model="yearMonth" class="ml--2" />
                </div>
                <div class="col-7">
                    <nts-dropdown v-model="actualTimeSelectedCode">
                        <option v-for="item in actualTimeOptionDisp" :value="item.code">
                            {{item.name}}
                        </option>
                    </nts-dropdown>
                </div>
            </div>
        </div>
        <div v-if="displayFormat == '1'">
            <div class="row">
                <div class="col-9">
                    <nts-date-input v-model="selectedDate" />
                </div>
                <div class="col-3">
                    <button type="button" class="btn btn-primary btn-block" v-on:click="openMenu">{{'KDWS03_23' | i18n}}</button>
                </div>
            </div>
        </div>
    
        <fix-table v-if="displayFormat == '0'" table-class="table table-bordered m-0 table-sm table-custom" :rowNumber="7" class="mx-n2" style="font-size: 11px" :key="resetTable">
            <thead class="uk-bg-headline">
                <tr>
                    <th c-width="56" style="height: 70px"></th>
                    <th v-for="(item, i) of displayHeaderLst" v-bind:style="{ 'background-color': item.color, 'word-wrap': 'break-word' }">{{item.headerText}}</th>
                    <th c-width="48"></th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(row, i) of displayDataLst">
                    <td v-bind:class="row.dateColor">{{row.date}}</td>
                    <td v-for="(cell, j) of row.rowData" v-bind:class="cell.class" v-bind:style="{ 'word-wrap': 'break-word' }">
                        {{cell.value == '0:00' || cell.value == '0.0' || cell.value == '0' ? '' : cell.value}}
                    </td>
                    <td>
                        <div style="text-align: right">
                            <span style="color: red" class="fa fa-exclamation-circle fa-lg" v-if="null != row.ERAL && row.ERAL.includes('ER')"></span>
                            <span style="color: red" class="fa fa-exclamation-triangle fa-lg" v-if="null != row.ERAL && !row.ERAL.includes('ER') && row.ERAL.includes('AL')"></span>
                            <span class="pl-1" v-on:click="openEdit(row.id)" v-if="row.date != ''">></span>
                        </div>
                    </td>
                </tr>
            </tbody>
            <tfoot>
                <tr>
                    <td>合計</td>
                    <td v-for="value in displaySumLst">{{value}}</td>
                    <td></td>
                </tr>
            </tfoot>
        </fix-table>
        <fix-table v-if="displayFormat == '1'" table-class="table table-bordered m-0 table-sm table-custom" :rowNumber="7" class="mx-n2" style="font-size: 11px" :key="resetTable">
            <thead class="uk-bg-headline">
                <tr>
                    <th c-width="58" style="height: 70px"></th>
                    <th v-for="(item, i) of displayHeaderLst" v-bind:style="{ 'background-color': item.color, 'word-wrap': 'break-word' }">{{item.headerText}}</th>
                    <th c-width="48"></th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(row, i) of displayDataLstEx">
                    <td style="font-size: 8.3px; word-wrap: break-word">{{row.employeeNameDis}}</td>
                    <td v-for="(cell, j) of row.rowData" v-bind:class="cell.class" v-bind:style="{ 'word-wrap': 'break-word' }">
                        {{cell.value == '0:00' || cell.value == '0.0' ? '' : cell.value}}
                    </td>
                    <td>
                        <div style="text-align: right">
                            <span style="color: red" class="fa fa-exclamation-circle fa-lg" v-if="null != row.ERAL && row.ERAL.includes('ER')"></span>
                            <span style="color: red" class="fa fa-exclamation-triangle fa-lg" v-if="null != row.ERAL && !row.ERAL.includes('ER') && row.ERAL.includes('AL')"></span>
                            <span class="pl-1" v-on:click="openEdit(row.id)" v-if="row.employeeNameDis != ''">></span>
                        </div>
                    </td>
                </tr>
            </tbody>
            <tfoot>
                <tr class="d-none">
                    <td>合計</td>
                    <td v-for="value in displayHeaderLst"></td>
                    <td></td>
                </tr>
            </tfoot>
        </fix-table>
        <div class="mx-n2 border-top" style="font-size: 10px" v-if="displayFormat == '1'">
            <div class="row mt-3 ">
                <div class="col-3 pr-1" v-bind:class="previousState">
                    <span class="mr-n1" v-on:click="previousPage">⇦前の20件</span>
                </div>
                <div class="col-6 text-center">
                    <span>{{itemStart}}～{{itemEnd}}件（{{displayDataLst.length}}件中）</span>
                </div>
                <div class="col-3 pl-2" v-bind:class="nextState">
                    <span v-on:click="nextPage">次の20件⇨</span>
                </div>
            </div>
        </div>
    </div>
</template>