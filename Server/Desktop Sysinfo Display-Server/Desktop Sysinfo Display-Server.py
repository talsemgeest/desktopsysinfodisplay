from jsonrpclib.SimpleJSONRPCServer import SimpleJSONRPCServer
import psutil

server = SimpleJSONRPCServer(('192.168.1.6', 48745))

def get_cpu_percent():
    return psutil.cpu_percent(0.1)

def get_mem_percent():
    return psutil.virtual_memory()[2]

server.register_function(get_cpu_percent)
server.register_function(get_mem_percent)
server.serve_forever()
