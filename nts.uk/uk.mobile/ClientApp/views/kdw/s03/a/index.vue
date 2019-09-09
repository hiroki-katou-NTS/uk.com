<template>
    <div class="kdw003a">
        <div v-if="displayFormat == '0'">
            <div class="row">
                <div class="col-9">
                    <nts-dropdown v-model="selectedEmployee">
                        <option v-for="item in lstEmployee" :value="item.code">
                            {{item.code}} &nbsp;&nbsp;&nbsp; {{item.businessName}}
                        </option>
                    </nts-dropdown>
                </div>
                <div class="col-3">
                    <button type="button" class="btn btn-primary btn-block" v-on:click="">{{'KDWS03_23' | i18n}}</button>
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
                    <button type="button" class="btn btn-primary btn-block" v-on:click="startPage">{{'KDWS03_23' | i18n}}</button>
                </div>
            </div>
        </div>
    
        <fix-table table-class="table table-bordered m-0 table-sm" :rowNumber="7" class="mx-n2" style="font-size: 11px" v-if="displayDataLst.length > 0" :key="resetTable">
            <thead class="uk-bg-headline">
                <tr >
                    <th class='align-middle' c-width="64" style="height: 70px"></th>
                    <th class='align-middle' v-for="(item, i) of displayHeaderLst" v-bind:style="{ 'background-color': item.color }">{{item.headerText}}</th>
                    <th class='align-middle' c-width="60"></th>
                </tr>
            </thead>
            <tbody v-if="displayFormat == '0'">
                <tr v-for="(row, i) of displayDataLst">
                    <td style="height: 50px" v-bind:class="row.dateColor">{{row.date}}</td>
                    <td class='align-middle' v-for="(cell, j) of row.rowData" v-bind:class="cell.class">{{cell.value == '0:00' ? '' : cell.value}}</td>
                    <td>
                        <span style="color: red" class="fa fa-exclamation-circle fa-lg"></span>
                        <span class="pl-1" v-on:click="openEdit(row.id)">></span>
                    </td>
                </tr>
            </tbody>
            <tbody v-if="displayFormat == '1'">
                <tr v-for="(row, i) of displayDataLstEx">
                    <td style="height: 50px">{{row.employeeName}}</td>
                    <td v-for="(cell, j) of row.rowData" v-bind:class="cell.class">{{cell.value == '0:00' ? '' : cell.value}}</td>
                    <td>
                        <span style="color: red" class="fa fa-exclamation-circle fa-lg"></span>
                        <span class="pl-1" v-on:click="openEdit(row.id)">></span>
                    </td>
                </tr>
            </tbody>
            <tfoot v-bind:class="displayFormat == '1' ? 'd-none' : ''">
                <tr>
                    <td>合計</td>
                    <td v-for="value in displaySumLst">{{value}}</td>
                    <td></td>
                </tr>
            </tfoot>
        </fix-table>
        <div class="row mt-3" v-if="displayFormat == '1'">
            <div class="col-3" v-bind:class="previousState">
                <span v-on:click="previousPage">⇦前の6件</span>
            </div>
            <div class="col-6 text-center">
                <span>{{itemStart}}～{{itemEnd}}件（{{displayDataLst.length}}件中）</span>
            </div>
            <div class="col-3" v-bind:class="nextState">
                <span v-on:click="nextPage">次の6件⇨</span>
            </div>
        </div>
    </div>
</template>