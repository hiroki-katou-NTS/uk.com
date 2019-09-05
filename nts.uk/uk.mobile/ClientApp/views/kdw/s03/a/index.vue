<template>
    <div class="kdw003a">
        <div class="row">
            <div class="col-9">
                <nts-dropdown v-model="selectedEmployee">
                    <option v-for="item in lstEmployee" :value="item.code">
                        {{item.code}} &nbsp;&nbsp;&nbsp; {{item.businessName}}
                    </option>
                </nts-dropdown>
            </div>
            <div class="col-3">
                <button type="button" class="btn btn-primary btn-block" v-on:click="startPage">{{'KDWS03_23' | i18n}}</button>
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
        <fix-table table-class="table table-bordered m-0  table-sm" :rowNumber="6" class="mx-n2" style="font-size: 11px" v-if="displayDataLst.length > 0" :key="resetTable">
            <thead class="uk-bg-headline">
                <tr>
                    <th c-width="64" style="height: 70px"></th>
                    <th v-for="(item, i) of displayDataLst[0].rowData"  v-bind:style="{ 'background-color': item.color }">{{item.headerText}}</th>
                    <th c-width="60"></th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(row, i) of displayDataLst" >
                    <td style="height: 50px">{{row.date}}</td>
                    <td v-for="(cell, j) of row.rowData" >{{cell.value}}</td>
                    <td>
                        <span style="color: red" class="fa fa-exclamation-circle fa-lg"></span>
                        <span class="pl-1" v-on:click="">></span>
                    </td>
                </tr>
            </tbody>
            <tfoot>
                <tr>
                    <td>合計</td>
                    <td v-for="(item, i) of displayDataLst[0].rowData" >{{i}}</td>
                    <td></td>
                </tr>
            </tfoot>
        </fix-table>
    </div>
</template>