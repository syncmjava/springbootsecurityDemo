$(document).ready(function() {

	var url = $("#ctx").val() + "/tables";
	$('#example').DataTable( {
        "ajax": url,
        "columns": [
            { "data": "id" },
            { "data": "category_name" },
            { "data": "date" },
            { "data": "amount" },
            { "data": "name" }
        ],
        "columnDefs": [
            {
                "targets": [ 0 ],
                "visible": false,
                "searchable": false
            }
        ]
    } );
    

	var compareUrl = $("#ctx").val() + "/detail?id=";
    $('#example tbody').on( 'click', 'tr', function () {
    	var table = $('#example').DataTable();
        if (table) {
        	var data = table.row(this._DT_RowIndex).data();
        	if (data) {
                window.location.href = compareUrl+data.id;
        	}
        }
        
    } );
} );
