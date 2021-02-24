package main

import (
	"bytes"
	"encoding/json"
	"io"
	"reflect"
	"strings"
)

// JSONEqual compares the JSON from two Readers.
func JSONEqual(a, b io.Reader) (bool, error) {
	var j, j2 interface{}
	d := json.NewDecoder(a)
	if err := d.Decode(&j); err != nil {
		return false, err
	}
	d = json.NewDecoder(b)
	if err := d.Decode(&j2); err != nil {
		return false, err
	}
	return reflect.DeepEqual(j2, j), nil
}

// JSONBytesEqual compares the JSON in two byte slices.
func JSONBytesEqual(a, b []byte) (bool, error) {
	var j, j2 interface{}
	if err := json.Unmarshal(a, &j); err != nil {
		return false, err
	}
	if err := json.Unmarshal(b, &j2); err != nil {
		return false, err
	}
	return reflect.DeepEqual(j2, j), nil
}

func main() {
	j1 := []byte(`{"k":"hi"}`)
	println("is valid: ", json.Valid(j1))

	j2 := []byte(`{"k": "hi \" {hello}\""}`)
	j3 := []byte(`{"k":"hi \" {hello}\""}`)

	d := `{"row_pos":7,"col_pos":1,"native_ad_type":"clips","ad_load_error_code":"java.net.UnknownHostException: Unable to resolve host \\\"graph.facebook.com\\\": No address associated with hostname","ujm_experiment_ids":"EX_1596562992464_1594011427330,EX_1596562992464_1594011271723","ad_provider":"FACEBOOK","pageid":"discovery","subs_mode":"free","subs_type":"Offer","subscription_trigger":"DEFAULT","programid":"b4e63ecccbf6d3f99674e4f77383da5f.mm.default","ip":"69.160.24.255","vuserid":"30dfd310-f3dc-468b-ba9a-9e9eb420f433","maidenlaunch":"false","identity":"393e1405f64d9237671bb180","identity_type":"PARTNER_ID","network":"4g","carrier_name":"OOREDOO","carrier_id":"58","msisdn":"393e1405f64d9237671bb180","offer_id":"carrier.58","offer_partner":"Ooredoo","privilege_type":"Offer","privileges":"[PREMIUM_GRANTED, SHOW_ADS, DOWNLOADS_ALLOWED, SP_CONTENT_ALLOWED, CONCURRENCY_APPLICABLE]","original_trigger":"discovery","app_type":"installed-app","is_tablet_device":"false","um_session_id":"Kex3zasgPHv66G7AJebBPyVFV6ZX8ohG"}`
	str := []byte(d)
	var rr bytes.Buffer
	json.Compact(&rr, str)
	ss := rr.String()
	println(strings.Compare(ss, d))

	println(`$$$$$$$$$$$$$$`)
	println(string(ss))
	println(`==============`)
	println(string(str))
	println(`$$$$$$$$$$$$$$`)

	var out bytes.Buffer
	e := json.Compact(&out, j2)

	var p interface{}
	if e != nil {
		panic(p)
	}

	q := out.String()
	println(q)
	println(string(j2))

	println(strings.Compare(string(j2), q))

	var ji2 interface{}
	var ji3 interface{}

	json.Unmarshal(j2, &ji2)
	json.Unmarshal(j3, &ji3)

	r, _ := JSONBytesEqual(j2, j3)
	println("\nare equals? ", r)

	println()
	println("bye")
}
