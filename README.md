# scala-chunked-transfer
Example of scala3 chunked transfer encoding http server 

I was initially confused because i didnt understand that Scala treats \n and \r\n both Carriage return sequences the same, which initially resulted in many errors. 



# Chunked-transfer encoding

In the HTTP 1/1 protocal, servers send a `Content-length` which indicates the size of the body that will be returned in the HTTP response. The entire response body is sent along with the rest of the HTTP entity to the client, which reads N bytes from the response body, where N is the `Content-length`. For example, if a server wishes to send the response "Hello World!", the content length is 16, which is added as a header to the HTTP response.

```
HTTP/1.1 200 OK
Content-Type: text/plain
Content-Length: 12

Hello World!
```

For small amounts of data, this approach works well. However problems can arise when the response body is extremely large, because the server must load the entire response body into memory before returning it to the client. HTTP 1/1 provides an alternative to the `Content-length` header which is `Transfer Encoding: Chunked`. In this scenario the response body is sent to the client in chunk along with the size of the chunk in bytes, which are read through a persistent connection. To indicate the end of the stream, the server sends a chunk of 0 length, followed by carriage-return, allowing the client to close the connection, as illustrated in the following example. 


```
HTTP/1.1 200 OK
Content-Type: text/plain
Transfer-Encoding: chunked

7
Mozilla

7
Mozilla

0
```

The server can choose to write as many individual chunks to the stream as it wants ( splitting the body into too many chunks will often not be optimal). 

<img width="719" alt="Screenshot 2023-11-05 at 4 00 46 PM" src="https://github.com/pwharned/scala-chunked-transfer/assets/52669420/b2092845-89cf-4ed5-be94-f9066f102d3a">
