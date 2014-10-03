def stringify_keys(data)
  after = {}
  data.each {|k,v| after[k.to_s] = v}
  after
end

def startNotify(obj)
  p obj
  data = {code: "00"}
  after = stringify_keys(data)

  obj.notify(data)
  obj.notify(after)
end

def block
  yield
end

puts "require"

def hello
  puts "hello method"
end
